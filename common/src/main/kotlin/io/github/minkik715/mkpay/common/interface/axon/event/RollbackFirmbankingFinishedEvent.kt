package io.github.minkik715.mkpay.common.`interface`.axon.event

data class RollbackFirmbankingFinishedEvent(
    val rollbackFirmbankingId: String ="",
    val membershipId: Long = 0L,
    val rollbackFirmbankingAggregateIdentifier: String = "",
    val resultCode: Int = -1
)

