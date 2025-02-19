package io.github.minkik715.mkpay.banking.adapter.out.external.bank

data class GetBankAccountResponse(
    val bankName: String,
    val bankAccountNumber: String,
    val isValid: Boolean
)