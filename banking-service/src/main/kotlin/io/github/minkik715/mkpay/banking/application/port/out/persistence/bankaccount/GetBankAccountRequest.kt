package io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount

data class GetBankAccountRequest(
    val bankName: String,
    val bankAccountNumber: String
)
