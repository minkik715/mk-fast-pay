package io.github.minkik715.mkpay.money.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.money.application.port.`in`.IncreaseMoneyCommand
import io.github.minkik715.mkpay.money.application.port.`in`.MoneyUseCase
import io.github.minkik715.mkpay.money.application.port.out.MemberMoneyPort
import io.github.minkik715.mkpay.money.application.port.out.MoneyPort
import io.github.minkik715.mkpay.money.application.port.out.UpdateMoneyChangingStatusRequest
import io.github.minkik715.mkpay.money.domain.*
import jakarta.transaction.Transactional
import java.util.Date
import java.util.UUID

@UseCase
class MoneyService(
    private val moneyPort: MoneyPort,
    private val memberMoneyPort: MemberMoneyPort

):  MoneyUseCase{

    @Transactional
    override fun requestIncreaseMoney(command: IncreaseMoneyCommand): MoneyChangingRequest {

        // 머니의 충전
        // 1 고객 정보가 정상인지 확인 (멤버)

        // 2. 고객의 연동된 계좌가 있는지 정상적인지, 잔액이 충분한지 (뱅킹)

        // 3. 법인 계좌 상태가 정산인지 (뱅킹)

        // 4. 증액을 위한 기록 요청 상태로 생성 (저장)
        val moneyChangingRequest = moneyPort.createIncreaseMoneyChanging(
            TargetMembershipId(command.targetMembershipId),
            ChangingTypeField(MoneyChangingRequest.ChangingType.INCREASING),
            ChangingMoneyAmount(command.amount),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.REQUESTED),
            CreatedAt(Date(System.currentTimeMillis()))
        )

        // 5. 펌뱅킹 수행사고 ( 고객의연동된 계좌 -> 패캠패에 법인 게좌) (뱅킹)

        //6-1. 성공
        memberMoneyPort.increaseMemberMoney(MembershipId(command.targetMembershipId), Balance(command.amount), LinkedBankAccount(true))

        moneyPort.updateMoneyChangingStatus(UpdateMoneyChangingStatusRequest(
            MoneyChangingRequestId(moneyChangingRequest.moneyChangingRequestId),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.SUCCEEDED),
            UUID.randomUUID().toString()
        ))

        return moneyChangingRequest
        //6-2. 실패


    }
}