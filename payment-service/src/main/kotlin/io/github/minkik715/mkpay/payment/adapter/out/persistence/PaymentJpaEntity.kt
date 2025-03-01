package io.github.minkik715.mkpay.payment.adapter.out.persistence

import io.github.minkik715.mkpay.payment.domain.*
import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "payment")
class PaymentJpaEntity(
    private val membershipId: Long,

    private var price: Int,

    private var franchiseId: Long,

    private var franchiseFeeRate: Double,

    private var paymentStatus: Int,

    private var approvedAt: Date? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var paymentId: Long = 0

    fun toDomain(): Payment {
        return Payment.generatePayment(
            PaymentId(paymentId),
            MembershipId(membershipId),
            Price(price),
            FranchiseId(franchiseId),
            FranchiseFeeRate(franchiseFeeRate),
            ApprovedAt(approvedAt),
            PaymentStatus(paymentStatus)
        )
    }
}