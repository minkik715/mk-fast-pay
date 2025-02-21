package io.github.minkik715.mkpay.remittance.application.port.out.membership

data class MembershipStatus(
    val membershipId: Long,
    val isValid: Boolean
)
