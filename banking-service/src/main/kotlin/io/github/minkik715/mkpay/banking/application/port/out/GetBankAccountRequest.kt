package io.github.minkik715.mkpay.banking.application.port.out

data class GetBankAccountRequest(
    val bankName: String,
    val bankAccountNumber: String
)
