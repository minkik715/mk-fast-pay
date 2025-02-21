package io.github.minkik715.mkpay.membership.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating

data class FindMembershipCommand(
    val membershipId: Long
): SelfValidating<io.github.minkik715.mkpay.membership.application.port.`in`.FindMembershipCommand>() {
    init {
        super.validateSelf()
    }
}