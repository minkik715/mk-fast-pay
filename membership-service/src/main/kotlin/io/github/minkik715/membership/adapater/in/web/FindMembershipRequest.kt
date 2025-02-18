package io.github.minkik715.membership.adapater.`in`.web

import io.github.minkik715.common.WebAdapter
import org.springframework.web.bind.annotation.RestController


data class FindMembershipRequest(
    val membershipId: String
)
