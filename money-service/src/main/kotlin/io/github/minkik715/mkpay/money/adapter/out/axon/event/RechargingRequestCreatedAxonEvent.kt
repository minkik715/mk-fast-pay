package io.github.minkik715.mkpay.money.adapter.out.axon.event

data class RechargingRequestCreatedAxonEvent(
    val aggregateIdentifier: String ="",
    val rechargingRequestId: String = "",
    val membershipId: Long = 0L,
    val amount: Int = 0,

    val bankAccountAggregateIdentifier: String ="",
    val bankAccountNumber: String = "",
    val bankName: String = ""
)
