package io.github.minkik715.mkpay.money.adapter.out.axon.saga

import io.github.minkik715.mkpay.common.`interface`.axon.command.CheckRegisteredBankAccountAxonCommand
import io.github.minkik715.mkpay.common.`interface`.axon.command.RequestFirmbankingAxonCommand
import io.github.minkik715.mkpay.common.`interface`.axon.command.RollbackFirmbankingRequestCommand
import io.github.minkik715.mkpay.common.`interface`.axon.event.CheckRegisteredBankAccountAxonEvent
import io.github.minkik715.mkpay.common.`interface`.axon.event.RequestFirmbankingFinishedEvent
import io.github.minkik715.mkpay.common.`interface`.axon.event.RollbackFirmbankingFinishedEvent
import io.github.minkik715.mkpay.money.adapter.out.axon.event.RechargingRequestCreatedAxonEvent
import io.github.minkik715.mkpay.money.application.port.out.MemberMoneyPort
import io.github.minkik715.mkpay.money.application.port.out.MoneyPort
import io.github.minkik715.mkpay.money.domain.Balance
import io.github.minkik715.mkpay.money.domain.LinkedBankAccount
import io.github.minkik715.mkpay.money.domain.MembershipId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@Saga
class MoneyRechargeSaga{

    @Transient
    @Autowired
    private lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestId")
    fun handle(event: RechargingRequestCreatedAxonEvent){
        println("Recharging request created: $event")

        val rechargingRequestId = event.rechargingRequestId

        val checkRegisteredBankAccountId = UUID.randomUUID().toString()
        SagaLifecycle.associateWith("checkRegisteredBankAccountId", checkRegisteredBankAccountId)

        // 춘전 요청이 시작됨

        // 뱅킹 계좌 등록 여부 확인하기. (Registered BankAccount Aggregate 활용)
        // CheckRegisteredBankAccountCommand -> check Bank Account
        commandGateway.send<String>(CheckRegisteredBankAccountAxonCommand(
            event.bankAccountAggregateIdentifier,
            event.membershipId,
            rechargingRequestId,
            checkRegisteredBankAccountId,
            event.bankAccountNumber,
            event.bankName,
            event.amount,
        )).exceptionally {
            throw it
        }

        // axon server -> Banking-Service -> Common interface
    }

    @SagaEventHandler(associationProperty = "checkRegisteredBankAccountId")
    fun handle(event: CheckRegisteredBankAccountAxonEvent){
        println("Checking account with id: $event")

        val isValid = event.isChecked

        if(isValid){

        }else{
            // 로그에 저장 및 유저에게 실패 알림 보내기
            return
        }


        val requestFirmbankingId = UUID.randomUUID().toString()

        SagaLifecycle.associateWith("requestFirmbankingId", requestFirmbankingId)
        println("requestFirmbankingId: $requestFirmbankingId")

        commandGateway.send<String>(
            RequestFirmbankingAxonCommand(
                requestFirmbankingId,
                event.firmbankingRequestAggregateIdentifier,
                event.rechargingRequestId,
                event.membershipId,
                event.fromBankName,
                event.fromBankAccountNumber,
                "fastcampus",
                "123456789",
                event.amount
            )
        )
    }

    @SagaEventHandler(associationProperty = "requestFirmbankingId")
    fun handle(event: RequestFirmbankingFinishedEvent, memberMoneyPort: MemberMoneyPort){
        println("Request finished: $event")

        val resultCode: Int = event.status


        if(resultCode == 0){

        }else{
            // 보상 트랜 잭션

            val rollbackFirmbankingId = UUID.randomUUID().toString()
            SagaLifecycle.associateWith("rollbackFirmbankingId", rollbackFirmbankingId)

            commandGateway.send<String>(RollbackFirmbankingRequestCommand(
                rollbackFirmbankingId,
                UUID.randomUUID().toString(),
                event.rechargingRequestId,
                event.membershipId,
                event.toBankName,
                event.toBankAccountNumber,
                event.moneyAmount
            ))
            // 로그에 저장 및 유저에게 실패 알림 보내기
            return
        }

        runCatching {
            val memberMoney = memberMoneyPort.increaseMemberMoney(
                MembershipId(event.membershipId),
                Balance(event.moneyAmount),
                LinkedBankAccount(true)
            )
        }.onFailure {

        }


        //SagaLifecycle.associateWith("requestFirmBankingId", requestFirmbankingId)

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "rollbackFirmbankingId")
    fun handle(rollbackFirmbankingFinishedEvent: RollbackFirmbankingFinishedEvent){
        println("Recharging request finished: $rollbackFirmbankingFinishedEvent")
        val rollbackFirmbankingId = rollbackFirmbankingFinishedEvent.rollbackFirmbankingId
    }

}