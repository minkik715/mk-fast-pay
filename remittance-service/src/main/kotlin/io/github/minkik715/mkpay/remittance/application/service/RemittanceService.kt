package io.github.minkik715.mkpay.remittance.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.remittance.application.port.`in`.RemittanceUseCase
import io.github.minkik715.mkpay.remittance.application.port.`in`.RequestRemittanceCommand
import io.github.minkik715.mkpay.remittance.application.port.out.RemittancePort
import io.github.minkik715.mkpay.remittance.application.port.out.banking.BankingPort
import io.github.minkik715.mkpay.remittance.application.port.out.membership.MembershipPort
import io.github.minkik715.mkpay.remittance.application.port.out.membership.MembershipStatus
import io.github.minkik715.mkpay.remittance.application.port.out.money.MoneyInfo
import io.github.minkik715.mkpay.remittance.application.port.out.money.MoneyPort
import io.github.minkik715.mkpay.remittance.domain.*
import kotlin.math.ceil


@UseCase
class RemittanceService(
    private val remittancePort: RemittancePort,
    private val membershipPort: MembershipPort,
    private val moneyPort: MoneyPort,
    private val bankingPort: BankingPort
) : RemittanceUseCase{

    override fun requestRemittance(command: RequestRemittanceCommand): RemittanceRequest {
        // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)

        val remittanceRequest = remittancePort.createRemittanceRequestHistory(
            fromMembershipId = FromMembershipId(command.fromMembershipId),
            toMembershipId = ToMembershipId(command.toMembershipId),
            toBankName = ToBankName(command.toBankName),
            toBankAccountNumber = ToBankAccountNumber(command.toBankAccountNumber),
            remittanceType = RemittanceType(command.remittanceType),
            amount = Amount(command.amount),
            remittanceStatus = RemittanceStatus("request")
        )

        // 1. from 멤버십 상태 확인 (membership Svc)
        val membershipStatus: MembershipStatus = membershipPort.getMembershipStatus(command.fromMembershipId)
        if (!membershipStatus.isValid) {
            throw IllegalArgumentException("Invalid membership status")
        }

        // 2. 잔액 존재하는지 확인 (money svc)
        val moneyInfo: MoneyInfo = moneyPort.getMoneyInfo(command.fromMembershipId)

        // 잔액이 충분치 않은 경우. -> 충전이 필요한 경우
        if (moneyInfo.balance < command.amount) {
            // command.getAmount() - moneyInfo.getBalance()
            // 만원 단위로 올림하는 Math 함수
            val rechargeAmount = ceil((command.amount - moneyInfo.balance) / 10000.0).toInt() * 10000

            // 2-1. 잔액이 충분하지 않다면, 충전 요청 (money svc)
            val moneyResult: Boolean = moneyPort.requestMoneyRecharging(command.fromMembershipId, rechargeAmount)
            if (!moneyResult) {
                throw IllegalArgumentException("Invalid money info")
            }
        }

        // 3. 송금 타입 (고객/은행)
        if (command.remittanceType == 0) {
            // 3-1. 내부 고객일 경우
            // from 고객 머니 감액, to 고객 머니 증액 (money svc)
            val remittanceResult1: Boolean = moneyPort.requestMoneyDecrease(command.fromMembershipId, command.amount)
            val remittanceResult2: Boolean = moneyPort.requestMoneyIncrease(command.toMembershipId!!, command.amount)
            if (!remittanceResult1 || !remittanceResult2) {
                throw IllegalArgumentException("Invalid money info")
            }
        } else if (command.remittanceType == 1) {
            // 3-2. 외부 은행 계좌
            // 외부 은행 계좌가 적절한지 확인 (banking svc)
            val membershipBankingInfo =
                bankingPort.getMembershipBankingInfo(command.toBankName!!, command.toBankAccountNumber!!)
            if(membershipBankingInfo?.isValid !=true){
                throw IllegalArgumentException("Invalid membership banking info")
            }
            // 법인계좌 -> 외부 은행 계좌 펌뱅킹 요청 (banking svc)
            val remittanceResult: Boolean =
                bankingPort.requestFirmbanking(command.toBankName!!, command.toBankAccountNumber!!, command.amount)
            if (!remittanceResult) {
                throw IllegalArgumentException("Invalid money info")
            }
        }

        // 4. 송금 요청 상태를 성공으로 기록 (persistence layer)
        return remittancePort.updateRemittanceStatus(RemittanceRequestId(remittanceRequest.remittanceRequestId), RemittanceStatus("success"))
    }

}