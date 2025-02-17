package io.github.minkik715.membership.adapater.`in`.web

import io.github.minkik715.common.WebAdapter
import io.github.minkik715.membership.application.port.`in`.RegisterMembershipCommand
import io.github.minkik715.membership.application.port.`in`.RegisterMembershipUseCase
import io.github.minkik715.membership.domain.Membership
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class RegisterMembershipController(
    private val registerMembershipUseCase: RegisterMembershipUseCase
) {

    @PostMapping("/memberships/register")
    fun registerMembership(@RequestBody request: RegisterMembershipRequest): Membership {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = RegisterMembershipCommand(
            name = request.name,
            address = request.address,
            email = request.email,
            isCorp = request.isCorp,
            isValid = true
        )
        // Usecase
        return registerMembershipUseCase.registerMembership(command)
    }
}