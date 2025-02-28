package io.github.minkik715.mkpay.common.`interface`.axon.event

data class RequestFirmbankingFinishedEvent(
    val requestFirmbankingId: String = "",
    val rechargingRequestId: String = "",
    val membershipId: Long = 0L,
    val toBankName: String = "",
    val toBankAccountNumber: String = "",
    val moneyAmount: Int = 0,  // won
    val status: Int = 0,
    val requestFirmbankingAggregateIdentifier: String = ""
)
