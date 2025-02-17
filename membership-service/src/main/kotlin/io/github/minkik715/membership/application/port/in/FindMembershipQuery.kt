package io.github.minkik715.membership.application.port.`in`

import io.github.minkik715.common.SelfValidating

data class FindMembershipQuery(
    val membershipId: Long
): SelfValidating<FindMembershipQuery>() {
    init {
        super.validateSelf()
    }
}