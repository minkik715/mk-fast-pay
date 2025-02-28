package io.github.minkik715.mkpay.banking.adapter.out.axon.event

data class CreateRegisteredBankAccountAxonEvent (
    val aggregateIdentifier: String = "",

    val membershipId: Long = 0L,
    val bankName: String = "",
    val bankAccountNumber: String = "",
)
