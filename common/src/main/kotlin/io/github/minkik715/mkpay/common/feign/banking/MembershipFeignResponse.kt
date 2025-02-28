package io.github.minkik715.mkpay.common.feign.banking

data class BankAccountFeignResponse(
    val aggregateIdentifier: String = "",
    val bankAccountId: Long,
    val membershipId: Long,
    val bankName: String,
    val bankAccountNumber: String,
    val linkedStatusIsValid: Boolean,
)
