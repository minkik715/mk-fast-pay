package io.github.minkik715.membership.adapater.`in`.web

data class RegisterMembershipRequest(
    val name: String,
    val address: String,
    val email: String,
    val isCorp: Boolean,
)
