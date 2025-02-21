package io.github.minkik715.mkpay.common.feign.membership

data class MembershipFeignResponse(
    val membershipId: Long,
    val name: String,
    val email: String,
    val address: String,
    val isValid: Boolean,
    val isCorp: Boolean,
)
