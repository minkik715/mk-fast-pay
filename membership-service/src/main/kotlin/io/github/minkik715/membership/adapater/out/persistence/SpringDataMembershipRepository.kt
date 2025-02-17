package io.github.minkik715.membership.adapater.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataMembershipRepository: JpaRepository<MembershipJpaEntity, Long> {
}