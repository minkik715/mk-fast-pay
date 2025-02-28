package io.github.minkik715.mkpay.banking.adapter.out.axon.aggregate

import io.github.minkik715.mkpay.banking.adapter.out.axon.command.RequestFirmbankingAxonCommand
import io.github.minkik715.mkpay.banking.adapter.out.axon.command.UpdateFirmbankingAxonCommand
import io.github.minkik715.mkpay.banking.adapter.out.axon.event.RequestFirmbankingAxonEvent
import io.github.minkik715.mkpay.banking.adapter.out.axon.event.UpdateFirmbankingAxonEvent
import io.github.minkik715.mkpay.banking.application.port.out.external.BankExternalPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmBankingPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmbankingExternalRequest
import io.github.minkik715.mkpay.banking.domain.*
import io.github.minkik715.mkpay.common.`interface`.axon.command.RollbackFirmbankingRequestCommand
import io.github.minkik715.mkpay.common.`interface`.axon.event.RequestFirmbankingFinishedEvent
import io.github.minkik715.mkpay.common.`interface`.axon.event.RollbackFirmbankingFinishedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateLifecycle.apply
import java.util.*

@Aggregate
class FirmbankingRequestAggregate() {
    @AggregateIdentifier
    private lateinit var id: String
    private var fromBankName:String = ""
    private var fromBankAccountNumber:String = ""
    private var toBankName:String = ""
    private var toBankAccountNumber:String = ""

    private var moneyAmount: Int = 0;

    private var firmbankingStatus: Int = -1

    @CommandHandler
    constructor(cmd: RequestFirmbankingAxonCommand): this() {
        println("CreateFirmbankingRequestAxonCommand called")
        apply(RequestFirmbankingAxonEvent(
            aggregateIdentifier = cmd.aggregateIdentifier,
            fromBankName = cmd.fromBankName,
            fromBankAccountNumber = cmd.fromBankAccountNumber,
            toBankName = cmd.toBankName,
            toBankAccountNumber = cmd.toBankAccountNumber,
            moneyAmount = cmd.moneyAmount,
            firmbankingStatus = cmd.firmbankingStatus
        ))
    }

    @EventSourcingHandler
    fun on(event: RequestFirmbankingAxonEvent) {
        println("CreateFirmbankingRequestAxonEvent called")

        this.id = event.aggregateIdentifier
        this.fromBankName = event.fromBankName
        this.fromBankAccountNumber = event.fromBankAccountNumber
        this.toBankName = event.toBankName
        this.toBankAccountNumber = event.toBankAccountNumber
        this.moneyAmount = event.moneyAmount
        this.firmbankingStatus = event.firmbankingStatus
    }

    @CommandHandler
    fun handle(cmd: UpdateFirmbankingAxonCommand):FirmbankingRequestAggregate  {
        println("UpdateFirmbankingRequestAxonCommand called")
        apply(
            UpdateFirmbankingAxonEvent(
                aggregateIdentifier = cmd.aggregateIdentifier,
                firmbankingRequestId = cmd.firmbankingRequestId,
                firmbankingStatus = cmd.firmbankingStatus
            )
        )
        return this
    }

    @EventSourcingHandler
    fun on(event: UpdateFirmbankingAxonEvent) {
        println("UpdateFirmbankingRequestAxonEvent called")
        this.id = event.aggregateIdentifier
        this.firmbankingStatus = event.firmbankingStatus
    }


    @CommandHandler
    fun handle(cmd: io.github.minkik715.mkpay.common.`interface`.axon.command.RequestFirmbankingAxonCommand,
               requestFirmBankingPort: FirmBankingPort,
               bankExternalPort: BankExternalPort
    ) {
        println("RequestFirmbankingAxonCommand called $cmd")

        id = cmd.aggregateIdentifier

        val firmbankingResult = requestFirmBankingPort.createFirmBankingRequest(
            FromBankName(cmd.fromBankName),
            FromBankAccountNumber(cmd.fromBankAccountNumber),
            ToBankName(cmd.toBankName),
            ToBankAccountNumber(cmd.toBankAccountNumber),
            MoneyAmount(cmd.moneyAmount),
            FirmbankingStatus(0),
            FirmbankingRequestAggregateIdentifier(cmd.requestFirmbankingId)
        )

        val externalFirmbankingResult = bankExternalPort.requestExternalFirmbanking(
            FirmbankingExternalRequest(
                firmbankingResult.fromBankName,
                firmbankingResult.fromBankAccountNumber,
                firmbankingResult.toBankName,
                firmbankingResult.toBankAccountNumber,
                firmbankingResult.moneyAmount
            )
        )

        val resultCode = externalFirmbankingResult.resultCode

        apply(
            RequestFirmbankingFinishedEvent(
                cmd.requestFirmbankingId,
                cmd.rechargeRequestId,
                cmd.membershipId,
                cmd.toBankName,
                cmd.toBankAccountNumber,
                cmd.moneyAmount,
                resultCode,
                id
            )
        )

    }

    @CommandHandler
    constructor(cmd : RollbackFirmbankingRequestCommand,
                requestFirmBankingPort: FirmBankingPort,
                bankExternalPort: BankExternalPort
        ): this(){
        println("RequestFirmbankingAxonCommand called $cmd")

        id = cmd.aggregateIdentifier

        val firmbankingResult = requestFirmBankingPort.createFirmBankingRequest(
            FromBankName("fastcampus"),
            FromBankAccountNumber("12314234234"),
            ToBankName(cmd.bankName),
            ToBankAccountNumber(cmd.bankAccountNumber),
            MoneyAmount(cmd.moneyAmount),
            FirmbankingStatus(0),
            FirmbankingRequestAggregateIdentifier(id)
        )

        val externalFirmbankingResult = bankExternalPort.requestExternalFirmbanking(
            FirmbankingExternalRequest(
                firmbankingResult.fromBankName,
                firmbankingResult.fromBankAccountNumber,
                firmbankingResult.toBankName,
                firmbankingResult.toBankAccountNumber,
                firmbankingResult.moneyAmount
            )
        )

        val resultCode = externalFirmbankingResult.resultCode

        apply(
            RollbackFirmbankingFinishedEvent(
                cmd.rollbackFirmbankingId,
                cmd.membershipId,
                id,
                resultCode
            )
        )
    }
}