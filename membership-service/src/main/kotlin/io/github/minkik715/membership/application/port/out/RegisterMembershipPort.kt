package io.github.minkik715.membership.application.port.out

import io.github.minkik715.membership.adapater.out.persistence.MembershipJpaEntity
import io.github.minkik715.membership.domain.*

interface RegisterMembershipPort {

    fun createMembership(
        membershipName: MembershipName,
        membershipAddress: MembershipAddress,
        membershipEmail: MembershipEmail,
        membershipIsValid: MembershipIsValid,
        membershipIsCorp: MembershipIsCorp,
    ): MembershipJpaEntity
}