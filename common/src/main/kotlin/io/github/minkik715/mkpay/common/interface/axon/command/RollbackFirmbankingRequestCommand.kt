package io.github.minkik715.mkpay.common.`interface`.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RollbackFirmbankingRequestCommand(
    val rollbackFirmbankingId: String = "",
    @TargetAggregateIdentifier
    val aggregateIdentifier: String = "",
    val rechargeRequestId: String = "",
    val membershipId: Long = 0L,
    val bankName: String = "",
    val bankAccountNumber: String = "",
    val moneyAmount: Int = 0
)
