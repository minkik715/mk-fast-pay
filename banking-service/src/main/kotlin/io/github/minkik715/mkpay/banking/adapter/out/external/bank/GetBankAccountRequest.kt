package io.github.minkik715.mkpay.banking.adapter.out.external.bank

data class GetBankAccountRequest(
    val bankName: String,
    val bankAccountNumber: String
)
