package io.github.minkik715.mkpay.common.`interface`.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RequestFirmbankingAxonCommand(
    val requestFirmbankingId: String = "",
    @TargetAggregateIdentifier
    val aggregateIdentifier: String = "",
    val rechargeRequestId: String = "",
    val membershipId: Long = 0L,
    val fromBankName: String = "",
    val fromBankAccountNumber: String = "",
    val toBankName: String = "",
    val toBankAccountNumber: String = "",
    val moneyAmount: Int = 0 // only won
)

