package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.money.domain.MemberMoney
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataMemberMoneyRepository: JpaRepository<MemberMoneyJpaEntity, Long>{
    fun findByMembershipId(membershipId: Long): MemberMoneyJpaEntity?
}
