package io.github.minkik715.mkpay.banking.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataBankAccountRepository: JpaRepository<BankAccountJpaEntity, Long> {

    fun findAllByMembershipId(membershipId: Long): List<BankAccountJpaEntity>
}