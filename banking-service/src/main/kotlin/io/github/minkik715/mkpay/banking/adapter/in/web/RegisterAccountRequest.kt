package io.github.minkik715.mkpay.banking.adapter.`in`.web

data class RegisterAccountRequest(
    val membershipId: Long,
    val bankName: String,
    val bankAccountNumber: String,
    val linkedStatusIsValid: Boolean,
)