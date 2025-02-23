package io.github.minkik715.mkpay.money.application.service

import RechargingMoneyTask
import io.github.minkik715.mkpay.common.CountDownLatchManger
import io.github.minkik715.mkpay.common.SubTask
import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.common.feign.banking.BankingFeign
import io.github.minkik715.mkpay.common.feign.membership.MembershipFeign
import io.github.minkik715.mkpay.money.application.port.`in`.IncreaseMoneyCommand
import io.github.minkik715.mkpay.money.application.port.`in`.MoneyUseCase
import io.github.minkik715.mkpay.money.application.port.out.MemberMoneyPort
import io.github.minkik715.mkpay.money.application.port.out.MoneyPort
import io.github.minkik715.mkpay.money.application.port.out.SendRechargingMoneyTaskPort
import io.github.minkik715.mkpay.money.application.port.out.UpdateMoneyChangingStatusRequest
import io.github.minkik715.mkpay.money.domain.*
import jakarta.transaction.Transactional
import java.util.Date
import java.util.UUID

@UseCase
class MoneyService(
    private val moneyPort: MoneyPort,
    private val memberMoneyPort: MemberMoneyPort,
    private val membershipFeign: MembershipFeign,
    private val bankingFeign: BankingFeign,
    private val sendRechargingMoneyTaskPort: SendRechargingMoneyTaskPort,
    private val countDownLatchManger: CountDownLatchManger
):  MoneyUseCase{

    @Transactional
    override fun requestIncreaseMoney(command: IncreaseMoneyCommand): MoneyChangingRequest {

        // 머니의 충전
        // 1 고객 정보가 정상인지 확인 (멤버)
        membershipFeign.getMembershipByMemberId(command.targetMembershipId).let {
            if(it.body?.isValid != true) throw IllegalArgumentException("IncreaseMoney command cannot be invalid")
        }


        // 2. 고객의 연동된 계좌가 있는지 정상적인지, 잔액이 충분한지 (뱅킹)
        bankingFeign.getMembershipByMemberId(command.targetMembershipId).let {
            if(it.body?.first()?.linkedStatusIsValid != true) throw IllegalArgumentException("IncreaseMoney command cannot be invalid")
        }
        // 3. 법인 계좌 상태가 정산인지 (뱅킹)

        // 4. 증액을 위한 기록 요청 상태로 생성 (저장)
        var moneyChangingRequest = moneyPort.createIncreaseMoneyChanging(
            TargetMembershipId(command.targetMembershipId),
            ChangingTypeField(MoneyChangingRequest.ChangingType.INCREASING),
            ChangingMoneyAmount(command.amount),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.REQUESTED),
            CreatedAt(Date(System.currentTimeMillis()))
        )

        // 5. 펌뱅킹 수행사고 ( 고객의연동된 계좌 -> 패캠패에 법인 게좌) (뱅킹)

        //6-1. 성공
        memberMoneyPort.increaseMemberMoney(MembershipId(command.targetMembershipId), Balance(command.amount), LinkedBankAccount(true))

        moneyChangingRequest = moneyPort.updateMoneyChangingStatus(UpdateMoneyChangingStatusRequest(
            MoneyChangingRequestId(moneyChangingRequest.moneyChangingRequestId),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.SUCCEEDED),
            UUID.randomUUID().toString()
        ))

        return moneyChangingRequest
        //6-2. 실패


    }

    @Transactional
    override fun requestIncreaseMoneyAsync(
        command: IncreaseMoneyCommand
    ): MoneyChangingRequest {

        var moneyChangingRequest = moneyPort.createIncreaseMoneyChanging(
            TargetMembershipId(command.targetMembershipId),
            ChangingTypeField(MoneyChangingRequest.ChangingType.INCREASING),
            ChangingMoneyAmount(command.amount),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.REQUESTED),
            CreatedAt(Date(System.currentTimeMillis()))
        )


        // 1. Subtask Task
        val validMemberTask = SubTask(
            subTaskName = "validMemberTask: 멤버십 유효성 검사",
            membershipId = command.targetMembershipId,
            taskType = "membership",
            status = "ready"
        )

        // Banking sub task
        // Banking Account Validation
        val validBankingAccountTask = SubTask(
            subTaskName = "validBankingAccountTask: 뱅킹 계좌 유효성 검사",
            membershipId = command.targetMembershipId,
            taskType = "banking",
            status = "ready"
        )

        val subTasks = listOf(validMemberTask, validBankingAccountTask)

        val mainTask = RechargingMoneyTask(
            taskId = UUID.randomUUID().toString(),
            taskName = "Increa Money Task: 머니 충전",
            subTasks = subTasks,
            moneyAmount = command.amount,
            membershipId = command.targetMembershipId,
            toBankName = "mkpay",
            toBankAccountNumber = "12345678532345"
        )

        // Amount Money Firmbanking

        // 2. Kafka Cluster Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(mainTask)
        // 3. Wait

        countDownLatchManger.addCountDownLatch(mainTask.taskId).await()

        //3-1. task-consumer 모두 Ok

        // 4. Task Result Consume
        val result = countDownLatchManger.getDataForKey(mainTask.taskId)

        if(result == "success"){
            // 5. 펌뱅킹 수행사고 ( 고객의연동된 계좌 -> 패캠패에 법인 게좌) (뱅킹)
            //6-1. 성공
            memberMoneyPort.increaseMemberMoney(MembershipId(command.targetMembershipId), Balance(command.amount), LinkedBankAccount(true))

            moneyChangingRequest = moneyPort.updateMoneyChangingStatus(UpdateMoneyChangingStatusRequest(
                MoneyChangingRequestId(moneyChangingRequest.moneyChangingRequestId),
                ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.SUCCEEDED),
                UUID.randomUUID().toString()
            ))
        }else{
            moneyChangingRequest = moneyPort.updateMoneyChangingStatus(UpdateMoneyChangingStatusRequest(
                MoneyChangingRequestId(moneyChangingRequest.moneyChangingRequestId),
                ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.FAILED),
                UUID.randomUUID().toString()
            ))
        }
        // 5. Consume ok, Logic
        countDownLatchManger.clean(mainTask.taskId)
        return moneyChangingRequest
    }
}