package io.github.minkik715.mkpay.banking.adapter.`in`.web

data class RequestFirmbankingRequest(
    val fromBankName: String,
    val fromBankAccountNumber:String,

    val toBankName: String,
    val toBankAccountNumber:String,

    val moneyAmount: Int
    )