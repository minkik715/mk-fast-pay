package io.github.minkik715.mkpay.payment.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating

data class RequestPaymentCommand(
    val membershipId: Long,

    val price: Int,

    val franchiseId: Long,
    val franchiseFeeRate: Double,
): SelfValidating<RequestPaymentCommand>() {

    init {
        this.validateSelf()
    }
}
