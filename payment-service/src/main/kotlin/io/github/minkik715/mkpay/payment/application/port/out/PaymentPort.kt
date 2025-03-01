package io.github.minkik715.mkpay.payment.application.port.out

import io.github.minkik715.mkpay.payment.domain.*

interface PaymentPort {
    fun requestPayment(
        membershipId: MembershipId,
        price: Price,
        franchiseId: FranchiseId,
        franchiseFeeRate: FranchiseFeeRate,
    ): Payment
}