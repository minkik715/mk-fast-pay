package io.github.minkik715.mkpay.common.`interface`.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CheckRegisteredBankAccountAxonCommand(

    @TargetAggregateIdentifier
    val aggregateIdentifier: String ="",

    val membershipId: Long = 0L,

    val rechargeRequestId: String ="",

    val checkRegisterBankAccountId: String ="",

    val bankAccountNumber: String = "",
    val bankName: String = "",
    val amount: Int = 0,
)
