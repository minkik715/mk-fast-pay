package io.github.minkik715.mkpay.payment.domain

import java.util.*

class Payment private constructor(
    val paymentId: Long,

    val membershipId: Long,

    val price: Int,

    val franchiseId: Long,
    val franchiseFeeRate: Double,
    val approvedAt: Date?,
    val paymentStatus: Int,
){
    companion object {
        fun generatePayment(
            paymentId: PaymentId,
            membershipId: MembershipId,
            price: Price,
            franchiseId: FranchiseId,
            franchiseFeeRate: FranchiseFeeRate,
            approvedAt: ApprovedAt,
            paymentStatus: PaymentStatus
        ): Payment {
            return Payment(
                paymentId = paymentId.paymentId,
                membershipId = membershipId.membershipId,
                price = price.price,
                franchiseId = franchiseId.franchiseId,
                franchiseFeeRate = franchiseFeeRate.franchiseFeeRate,
                approvedAt = approvedAt.approvedAt,
                paymentStatus = paymentStatus.paymentStatus
            )
        }
    }


}

@JvmInline
value class PaymentId constructor(val paymentId: Long)
@JvmInline
value class MembershipId constructor(val membershipId: Long)
@JvmInline
value class Price constructor(val price: Int)
@JvmInline
value class FranchiseId constructor(val franchiseId: Long)
@JvmInline
value class FranchiseFeeRate constructor(val franchiseFeeRate: Double)
@JvmInline
value class ApprovedAt constructor(val approvedAt: Date?)
@JvmInline
value class PaymentStatus constructor(val paymentStatus: Int)