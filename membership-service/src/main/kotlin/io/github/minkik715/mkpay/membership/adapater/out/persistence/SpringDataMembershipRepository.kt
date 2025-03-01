package io.github.minkik715.mkpay.membership.adapater.out.persistence

import io.github.minkik715.mkpay.membership.domain.Membership
import io.github.minkik715.mkpay.membership.domain.MembershipId
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataMembershipRepository: JpaRepository<MembershipJpaEntity, Long> {

    fun findByMembershipId(membershipId : Long) : MembershipJpaEntity
    fun findByAddress(address : String) : List<MembershipJpaEntity>
}