package io.github.minkik715.mkpay.banking.adapter.out.axon.aggregate

import io.github.minkik715.mkpay.banking.adapter.out.axon.command.CreateRegisteredBankAccountAxonCommand
import io.github.minkik715.mkpay.banking.adapter.out.axon.event.CreateRegisteredBankAccountAxonEvent
import io.github.minkik715.mkpay.banking.application.port.out.external.BankExternalPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.GetBankAccountRequest
import io.github.minkik715.mkpay.common.`interface`.axon.command.CheckRegisteredBankAccountAxonCommand
import io.github.minkik715.mkpay.common.`interface`.axon.event.CheckRegisteredBankAccountAxonEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import java.util.*
import org.axonframework.modelling.command.AggregateLifecycle.apply


@Aggregate
class RegisteredBankAccountAggregate() {
    @AggregateIdentifier
    private lateinit var id: String

    private var membershipId: Long = 0L

    private var bankName: String  = ""

    private var bankAccountNumber: String = ""

    @CommandHandler
    constructor(cmd: CreateRegisteredBankAccountAxonCommand): this() {
        println("CreateRegisteredBankAccountCommand called")
        apply(
            CreateRegisteredBankAccountAxonEvent(cmd.aggregateIdentifier,
                cmd.membershipId,cmd.bankName, cmd.bankAccountNumber))
    }

    @EventSourcingHandler
    fun on(event: CreateRegisteredBankAccountAxonEvent) {
        println("CreateRegisteredBankAccountAxonEvent called")
        this.id = event.aggregateIdentifier
        this.membershipId = event.membershipId
        this.bankName = event.bankName
        this.bankAccountNumber = event.bankAccountNumber
    }


    @CommandHandler
    fun handle(cmd: CheckRegisteredBankAccountAxonCommand, bankExternalPort: BankExternalPort) {
        println("CheckRegisteredBankAccountCommand called")
        println("cmd:  $cmd")
        id = cmd.aggregateIdentifier

        val bankAccountInfo =
            bankExternalPort.getBankAccountInfo(GetBankAccountRequest(cmd.bankName, cmd.bankAccountNumber))

        val isValidAccount = bankAccountInfo.isValid

        val firmbankingRequestId: String = UUID.randomUUID().toString()
        println("firmbankingRequestId: $firmbankingRequestId")
        apply(
            CheckRegisteredBankAccountAxonEvent(
                cmd.rechargeRequestId,
                cmd.checkRegisterBankAccountId,
                cmd.membershipId,
                isValidAccount,
                cmd.amount,
                firmbankingRequestId,
                bankAccountInfo.bankName,
                bankAccountInfo.bankAccountNumber
            )
        )
    }



}