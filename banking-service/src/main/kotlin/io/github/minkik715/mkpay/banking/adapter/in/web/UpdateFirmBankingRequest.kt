package io.github.minkik715.mkpay.banking.adapter.`in`.web

data class UpdateFirmBankingRequest(
    val firmbankingRequestId: Long,
    val status: Int
)