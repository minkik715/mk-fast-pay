package io.github.minkik715.mkpay.membership.adapater.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataMembershipRepository: JpaRepository<MembershipJpaEntity, Long> {
}