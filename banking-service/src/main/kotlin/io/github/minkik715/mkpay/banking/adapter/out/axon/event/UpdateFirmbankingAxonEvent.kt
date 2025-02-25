package io.github.minkik715.mkpay.banking.adapter.out.axon.event


data class UpdateFirmbankingAxonEvent(
    var aggregateIdentifier: String = "",
    var firmbankingRequestId: Long = 0L,
    var firmbankingStatus: Int = -1
)