package io.github.minkik715.mkpay.money.application.port.out.svc

data class RegisteredBankAccountAggregate(
    val aggregateIdentifier: String,
    val registeredBankAccountId: Long,
    val membershipId: Long,
    val bankName: String,
    val bankAccountNumber: String,
)
