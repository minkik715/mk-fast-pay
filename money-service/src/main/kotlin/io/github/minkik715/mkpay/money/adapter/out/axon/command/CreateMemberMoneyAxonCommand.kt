package io.github.minkik715.mkpay.money.adapter.out.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateMemberMoneyAxonCommand(
    val eventId: String = UUID.randomUUID().toString(),
    val membershipId: Long = 0L,
)