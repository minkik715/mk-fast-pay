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
    private val membershipUseCase: MembershipUseCase,
) {

    @GetMapping("/memberships/{membershipId}")
    fun getMembershipByMemberId(@PathVariable("membershipId") membershipId: Long): ResponseEntity<Membership> {
        val findMembershipCommand =
            FindMembershipCommand(membershipId)

        return ResponseEntity.ok(membershipUseCase.getMembershipByMembershipId(findMembershipCommand))
    }

    @PostMapping("/memberships")
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
        return membershipUseCase.registerMembership(command)
    }

    @PutMapping("/memberships")
    fun modifyMembership(@RequestBody request: ModifyMembershipRequest): Membership {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = ModifyMembershipCommand(
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