package io.github.minkik715.mkpay.membership.application.port.out

import io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity
import io.github.minkik715.mkpay.membership.application.port.`in`.ModifyMembershipCommand
import io.github.minkik715.membership.domain.*

interface MembershipOutPort {

    fun getMembershipByMembershipId(membershipId: io.github.minkik715.mkpay.membership.domain.MembershipId) : io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity

    fun createMembership(
        membershipName: io.github.minkik715.mkpay.membership.domain.MembershipName,
        membershipAddress: io.github.minkik715.mkpay.membership.domain.MembershipAddress,
        membershipEmail: io.github.minkik715.mkpay.membership.domain.MembershipEmail,
        membershipIsValid: io.github.minkik715.mkpay.membership.domain.MembershipIsValid,
        membershipIsCorp: io.github.minkik715.mkpay.membership.domain.MembershipIsCorp,
    ): io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity

    fun modifyMembership(
        membershipId: io.github.minkik715.mkpay.membership.domain.MembershipId,
        membershipName: io.github.minkik715.mkpay.membership.domain.MembershipName,
        membershipAddress: io.github.minkik715.mkpay.membership.domain.MembershipAddress,
        membershipEmail: io.github.minkik715.mkpay.membership.domain.MembershipEmail,
        membershipIsValid: io.github.minkik715.mkpay.membership.domain.MembershipIsValid,
        membershipIsCorp: io.github.minkik715.mkpay.membership.domain.MembershipIsCorp,
    ): io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity
}