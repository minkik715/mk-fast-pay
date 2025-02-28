package io.github.minkik715.mkpay.money.adapter.out.axon.aggregate

import io.github.minkik715.mkpay.money.adapter.out.axon.command.CreateMemberMoneyAxonCommand
import io.github.minkik715.mkpay.money.adapter.out.axon.command.IncreaseMoneyAxonCommand
import io.github.minkik715.mkpay.money.adapter.out.axon.command.RechargingRequestCreatedAxonCommand
import io.github.minkik715.mkpay.money.adapter.out.axon.event.CreateMemberMoneyAxonEvent
import io.github.minkik715.mkpay.money.adapter.out.axon.event.IncreaseMoneyAxonEvent
import io.github.minkik715.mkpay.money.adapter.out.axon.event.RechargingRequestCreatedAxonEvent
import io.github.minkik715.mkpay.money.application.port.out.svc.BankPort
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate


@Aggregate
class MemberMoneyAggregate(

) {
    @AggregateIdentifier
    private lateinit var id: String
    private var membershipId: Long = 0L
    private var balance: Int = 0

    constructor(id: String, membershipId: Long, balance: Int) : this() {
        this.id = id
        this.membershipId = membershipId
        this.balance = balance
    }

    @CommandHandler
    constructor(cmd: CreateMemberMoneyAxonCommand): this() {
        println("CreateMemberMoneyAxonCommand Gateway Handler")
        this.id = cmd.aggregateIdentifier
        apply(CreateMemberMoneyAxonEvent(cmd.aggregateIdentifier, cmd.membershipId))
    }

    @EventSourcingHandler
    fun on(event: CreateMemberMoneyAxonEvent) {
        println("CreateMemberMoneyAxonEvent EventSouring Handler?")
        println(event)
        this.id = event.id
        this.membershipId = event.membershipId
        this.balance = 0;
    }


    @CommandHandler
    fun handle(cmd: IncreaseMoneyAxonCommand): String{
        println("IncreaseMoneyAxonCommand Gateway Handler")
        this.id = cmd.aggregateIdentifier
        apply(IncreaseMoneyAxonEvent(cmd.targetMembershipId, cmd.amount, cmd.aggregateIdentifier))
        return cmd.aggregateIdentifier
    }

    @EventSourcingHandler
    fun on(event: IncreaseMoneyAxonEvent) {
        println("IncreaseMoneyAxonEvent EventSouring Handler")
        this.id = event.aggregateIdentifier
        this.membershipId = event.targetMembershipId
        this.balance += event.amount
    }


    @CommandHandler
    fun handle(cmd: RechargingRequestCreatedAxonCommand, bankPort: BankPort) {
        println("RechargingRequestCreatedAxonCommand Gateway Handler")
        this.id = cmd.aggregateIdentifier

        val bankAccountIdentifier = bankPort.getRegisteredBankAccount(cmd.membershipId)?: throw RuntimeException("bank account not found")

        println("bankAccountIdentifier: $bankAccountIdentifier")

        apply(RechargingRequestCreatedAxonEvent(
            cmd.aggregateIdentifier,
            cmd.rechargingRequestId,
            cmd.membershipId,
            cmd.amount,
            bankAccountIdentifier.aggregateIdentifier,
            bankAccountIdentifier.bankAccountNumber,
            bankAccountIdentifier.bankName
        )
        )
    }
}



