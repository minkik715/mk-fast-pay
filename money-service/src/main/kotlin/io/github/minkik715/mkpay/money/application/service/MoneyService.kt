package io.github.minkik715.mkpay.money.application.service

import RechargingMoneyTask
import io.github.minkik715.mkpay.common.CountDownLatchManger
import io.github.minkik715.mkpay.common.SubTask
import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.money.adapter.out.axon.command.CreateMemberMoneyAxonCommand
import io.github.minkik715.mkpay.money.adapter.out.axon.command.IncreaseMoneyAxonCommand
import io.github.minkik715.mkpay.money.adapter.out.axon.event.IncreaseMoneyAxonEvent
import io.github.minkik715.mkpay.money.application.port.`in`.CreateMemberMoneyCommand
import io.github.minkik715.mkpay.money.application.port.`in`.IncreaseMoneyCommand
import io.github.minkik715.mkpay.money.application.port.`in`.MoneyUseCase
import io.github.minkik715.mkpay.money.application.port.out.MemberMoneyPort
import io.github.minkik715.mkpay.money.application.port.out.MoneyPort
import io.github.minkik715.mkpay.money.application.port.out.SendRechargingMoneyTaskPort
import io.github.minkik715.mkpay.money.application.port.out.UpdateMoneyChangingStatusRequest
import io.github.minkik715.mkpay.money.application.port.out.svc.BankPort
import io.github.minkik715.mkpay.money.application.port.out.svc.MembershipPort
import io.github.minkik715.mkpay.money.domain.*
import jakarta.transaction.Transactional
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import java.util.Date
import java.util.UUID

@UseCase
class MoneyService(
    private val moneyPort: MoneyPort,
    private val memberMoneyPort: MemberMoneyPort,
    private val bankPort: BankPort,
    private val membershipPort: MembershipPort,
    private val sendRechargingMoneyTaskPort: SendRechargingMoneyTaskPort,
    private val countDownLatchManger: CountDownLatchManger,
    private val commandGateway: CommandGateway
):  MoneyUseCase{

    @Transactional
    override fun requestIncreaseMoney(command: IncreaseMoneyCommand): MoneyChangingRequest {

        // 머니의 충전
        // 1 고객 정보가 정상인지 확인 (멤버)
        membershipPort.getMembershipByMemberId(command.targetMembershipId).let {
            if(it?.isValid != true) throw IllegalArgumentException("Invalid membershipId")
        }


        // 2. 고객의 연동된 계좌가 있는지 정상적인지, 잔액이 충분한지 (뱅킹)
        bankPort.getAccountValidByMemberId(command.targetMembershipId) .let {
            if(it?.isValid != true) throw IllegalArgumentException("membership bank account invalid")
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

        // POD가 여러개일 경우 문제가 발생 Redis pub/sub를 통해서 문제해결 필요
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

    override fun requestIncreaseMoneyByEvent(command: IncreaseMoneyCommand) {
        memberMoneyPort.getMoneyByMembershipId(TargetMembershipId(command.targetMembershipId))?.let {
            val axonCommand = IncreaseMoneyAxonCommand(
                command.targetMembershipId,
                command.amount,
                it.aggregateIdentifier.aggregateIdentifier
            )
            commandGateway.send<String>(axonCommand)
                .exceptionally{ throwable ->
                    throw throwable
                }
                .join()
        }
    }

    override fun requestCreateMemberMoney(command: CreateMemberMoneyCommand) {
        val eventId = UUID.randomUUID().toString()
        val axonCommand = CreateMemberMoneyAxonCommand(eventId, command.membershipId)
        commandGateway.send<String>(axonCommand).whenComplete { aggregateIdentifier, throwable ->
            if (throwable != null) {
                throw throwable
            }
             memberMoneyPort.createMemberMoney(
                MembershipId(command.membershipId),
                LinkedBankAccount(false),
                MoneyAggregateIdentifier(aggregateIdentifier)
            )
        }.join()
    }

    @EventHandler
    fun on(event: IncreaseMoneyAxonEvent) {
        val moneyChangingRequest = moneyPort.createIncreaseMoneyChanging(
            TargetMembershipId(event.targetMembershipId),
            ChangingTypeField(MoneyChangingRequest.ChangingType.INCREASING),
            ChangingMoneyAmount(event.amount),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.REQUESTED),
            CreatedAt(Date(System.currentTimeMillis()))
        )

        memberMoneyPort.increaseMemberMoney(MembershipId(event.targetMembershipId), Balance(event.amount), LinkedBankAccount(true))

        moneyPort.updateMoneyChangingStatus(UpdateMoneyChangingStatusRequest(
            MoneyChangingRequestId(moneyChangingRequest.moneyChangingRequestId),
            ChangingMoneyStatusField(MoneyChangingRequest.ChangingMoneyStatus.SUCCEEDED),
            UUID.randomUUID().toString()
        ))

        //사용자에게 알람보내기
    }
}