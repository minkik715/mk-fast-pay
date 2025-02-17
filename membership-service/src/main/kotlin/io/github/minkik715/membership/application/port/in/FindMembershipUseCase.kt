package io.github.minkik715.membership.application.port.`in`

import io.github.minkik715.membership.domain.Membership

interface FindMembershipUseCase {
    fun getMembershipByMembershipId(query: FindMembershipQuery): Membership
}