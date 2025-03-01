package io.github.minkik715.mkpay.membership.application.port.out

import io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity
import io.github.minkik715.mkpay.membership.domain.*


interface MembershipOutPort {

    fun getMembershipByMembershipId(membershipId: MembershipId) :MembershipJpaEntity
    fun getMembershipByAddress(address: MembershipAddress) :Set<Membership>


    fun createMembership(
        membershipName: MembershipName,
        membershipAddress: MembershipAddress,
        membershipEmail: MembershipEmail,
        membershipIsValid: MembershipIsValid,
        membershipIsCorp: MembershipIsCorp,
    ): MembershipJpaEntity

    fun modifyMembership(
        membershipId: MembershipId,
        membershipName: MembershipName,
        membershipAddress: MembershipAddress,
        membershipEmail: MembershipEmail,
        membershipIsValid: MembershipIsValid,
        membershipIsCorp: MembershipIsCorp,
    ): MembershipJpaEntity
}