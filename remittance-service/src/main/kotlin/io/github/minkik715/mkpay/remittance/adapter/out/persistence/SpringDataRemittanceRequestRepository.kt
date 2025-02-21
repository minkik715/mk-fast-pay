package io.github.minkik715.mkpay.remittance.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataRemittanceRequestRepository : JpaRepository<RemittanceRequestJpaEntity, Long> {
    fun findByRemittanceRequestId(id: Long): RemittanceRequestJpaEntity?
}