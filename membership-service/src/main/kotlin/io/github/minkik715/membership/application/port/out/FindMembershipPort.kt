package io.github.minkik715.membership.application.port.out

import io.github.minkik715.membership.adapater.out.persistence.MembershipJpaEntity
import io.github.minkik715.membership.domain.MembershipId

interface FindMembershipPort {
    fun getMembershipByMembershipId(membershipId: MembershipId) : MembershipJpaEntity
}