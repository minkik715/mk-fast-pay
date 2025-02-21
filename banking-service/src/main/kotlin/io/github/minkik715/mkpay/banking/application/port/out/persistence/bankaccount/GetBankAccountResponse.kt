package io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount

data class GetBankAccountResponse(
    val bankName: String,
    val bankAccountNumber: String,
    val isValid: Boolean
)