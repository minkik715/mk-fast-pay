package io.github.minkik715.mkpay.membership.application.port.`in`

import io.github.minkik715.mkpay.common.SelfValidating

data class FindMembershipByAddressCommand(
    val addressName: String
): SelfValidating<FindMembershipByAddressCommand>() {
    init {
        super.validateSelf()
    }
}