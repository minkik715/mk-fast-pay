package io.github.minkik715.mkpay.payment.adapter.`in`.web

data class PaymentRequest(
    val membershipId: Long,

    val price: Int,

    val franchiseId: Long,
    val franchiseFeeRate: Double,


)
