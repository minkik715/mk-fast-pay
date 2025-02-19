package io.github.minkik715.mkpay.banking.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataFirmbankingRepository: JpaRepository<FirmBankingRequestJpaEntity, Long> {
}