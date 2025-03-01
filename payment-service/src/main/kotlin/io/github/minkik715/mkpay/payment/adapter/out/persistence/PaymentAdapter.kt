package io.github.minkik715.mkpay.payment.adapter.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.payment.application.port.out.PaymentPort
import io.github.minkik715.mkpay.payment.domain.*

@PersistenceAdapter
class PaymentAdapter(
    private val paymentRepository: SpringDataPaymentRepository
): PaymentPort {
    override fun requestPayment(
        membershipId: MembershipId,
        price: Price,
        franchiseId: FranchiseId,
        franchiseFeeRate: FranchiseFeeRate
    ): Payment {
        return paymentRepository.save(
            PaymentJpaEntity(
                membershipId = membershipId.membershipId,
                price = price.price,
                franchiseId = franchiseId.franchiseId,
                franchiseFeeRate = franchiseFeeRate.franchiseFeeRate,
                paymentStatus = 0,
            )
        ).toDomain()
    }
}
