package io.github.minkik715.membership.adapater.`in`.web

data class ModifyMembershipRequest(
    val id: Long,
    val name: String,
    val address: String,
    val email: String,
    val isValid: Boolean,
    val isCorp: Boolean,
)
