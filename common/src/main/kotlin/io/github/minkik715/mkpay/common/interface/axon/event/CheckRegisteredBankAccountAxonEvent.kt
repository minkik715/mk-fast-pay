package io.github.minkik715.mkpay.common.`interface`.axon.event

data class CheckRegisteredBankAccountAxonEvent(
    val rechargingRequestId: String = "",
    val checkRegisteredBankAccountId: String = "",
    val membershipId: Long = 0L,
    val isChecked: Boolean = false,
    val amount: Int = 0,
    val firmbankingRequestAggregateIdentifier: String = "",
    val fromBankName: String = "",
    val fromBankAccountNumber: String = ""
)