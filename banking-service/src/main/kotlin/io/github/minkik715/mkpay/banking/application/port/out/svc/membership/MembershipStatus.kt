package io.github.minkik715.mkpay.banking.application.port.out.svc.membership

data class MembershipStatus(
    val membershipId: Long,
    val isValid: Boolean
)