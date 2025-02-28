package io.github.minkik715.mkpay.common.`interface`.axon.event

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RollbackFirmbankingFinishedEvent(
    val rollbackFirmbankingId: String ="",
    val membershipId: Long = 0L,
    @TargetAggregateIdentifier
    val rollbackFirmbankingAggregateIdentifier: String = "",
    val resultCode: Int = -1
)

