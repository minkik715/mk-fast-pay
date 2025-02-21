package io.github.minkik715.mkpay.membership.adapater.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.membership.application.port.`in`.FindMembershipCommand
import io.github.minkik715.mkpay.membership.application.port.`in`.MembershipUseCase
import io.github.minkik715.mkpay.membership.application.port.`in`.ModifyMembershipCommand
import io.github.minkik715.mkpay.membership.application.port.`in`.RegisterMembershipCommand
import io.github.minkik715.mkpay.membership.domain.Membership
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebAdapter
@RestController
class MembershipController(
    private val membershipUseCase: io.github.minkik715.mkpay.membership.application.port.`in`.MembershipUseCase,
) {

    @GetMapping("/memberships/{membershipId}")
    fun getMembershipByMemberId(@PathVariable("membershipId") membershipId: Long): ResponseEntity<io.github.minkik715.mkpay.membership.domain.Membership> {
        val findMembershipCommand =
            io.github.minkik715.mkpay.membership.application.port.`in`.FindMembershipCommand(membershipId)

        return ResponseEntity.ok(membershipUseCase.getMembershipByMembershipId(findMembershipCommand))
    }

    @PostMapping("/memberships")
    fun registerMembership(@RequestBody request: io.github.minkik715.mkpay.membership.adapater.`in`.web.RegisterMembershipRequest): io.github.minkik715.mkpay.membership.domain.Membership {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = io.github.minkik715.mkpay.membership.application.port.`in`.RegisterMembershipCommand(
            name = request.name,
            address = request.address,
            email = request.email,
            isCorp = request.isCorp,
            isValid = true
        )
        // Usecase
        return membershipUseCase.registerMembership(command)
    }

    @PutMapping("/memberships")
    fun modifyMembership(@RequestBody request: io.github.minkik715.mkpay.membership.adapater.`in`.web.ModifyMembershipRequest): io.github.minkik715.mkpay.membership.domain.Membership {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = io.github.minkik715.mkpay.membership.application.port.`in`.ModifyMembershipCommand(
            id = request.id,
            name = request.name,
            address = request.address,
            email = request.email,
            isCorp = request.isCorp,
            isValid = request.isValid
        )
        // Usecase
        return membershipUseCase.modifyMembership(command)
    }
}