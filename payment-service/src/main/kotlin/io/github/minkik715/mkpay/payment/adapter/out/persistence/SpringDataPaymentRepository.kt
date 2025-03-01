package io.github.minkik715.mkpay.payment.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataPaymentRepository: JpaRepository<PaymentJpaEntity, Long>{
    fun findByMembershipId(membershipId: Long): PaymentJpaEntity?
}
