package io.github.minkik715.membership.application.port.`in`

import io.github.minkik715.membership.domain.Membership

interface MembershipUseCase {
    fun getMembershipByMembershipId(query: FindMembershipCommand): Membership

    fun registerMembership(command: RegisterMembershipCommand): Membership

    fun modifyMembership(command: ModifyMembershipCommand): Membership

}