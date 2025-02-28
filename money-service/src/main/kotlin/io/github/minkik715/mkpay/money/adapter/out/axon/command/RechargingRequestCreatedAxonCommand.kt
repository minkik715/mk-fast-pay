package io.github.minkik715.mkpay.money.adapter.out.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RechargingRequestCreatedAxonCommand(
    @TargetAggregateIdentifier
    val aggregateIdentifier: String ="",
    val rechargingRequestId: String = "",
    val membershipId: Long = 0L,
    val amount: Int = 0,
)
