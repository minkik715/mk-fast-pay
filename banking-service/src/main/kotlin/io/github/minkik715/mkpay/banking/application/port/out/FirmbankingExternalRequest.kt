package io.github.minkik715.mkpay.banking.application.port.out

data class FirmbankingExternalRequest(
    val fromBankName: String,
    val fromBankAccountNumber:String,

    val toBankName: String,
    val toBankAccountNumber:String,

    val moneyAmount: Int
)