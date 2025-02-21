package io.github.minkik715.mkpay.membership.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating

data class FindMembershipCommand(
    val membershipId: Long
): SelfValidating<FindMembershipCommand>() {
    init {
        super.validateSelf()
    }
}