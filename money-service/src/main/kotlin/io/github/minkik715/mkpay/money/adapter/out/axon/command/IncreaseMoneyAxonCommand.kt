package io.github.minkik715.mkpay.money.adapter.out.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class IncreaseMoneyAxonCommand (
    val targetMembershipId: Long = 0,
    val amount: Int = 0,

    @TargetAggregateIdentifier
    val aggregateIdentifier: String = ""
)