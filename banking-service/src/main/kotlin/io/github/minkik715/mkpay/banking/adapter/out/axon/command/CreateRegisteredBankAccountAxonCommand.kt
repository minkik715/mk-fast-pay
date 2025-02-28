package io.github.minkik715.mkpay.banking.adapter.out.axon.command

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateRegisteredBankAccountAxonCommand(
    @TargetAggregateIdentifier
    val aggregateIdentifier: String = "",

    val membershipId: Long = 0L,
    val bankName: String = "",
    val bankAccountNumber: String = "",
)
