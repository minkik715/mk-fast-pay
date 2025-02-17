package io.github.minkik715.membership.adapater.`in`.web

import io.github.minkik715.common.WebAdapter
import io.github.minkik715.membership.application.port.`in`.FindMembershipQuery
import io.github.minkik715.membership.application.port.`in`.FindMembershipUseCase
import io.github.minkik715.membership.application.port.`in`.RegisterMembershipUseCase
import io.github.minkik715.membership.domain.Membership
import io.github.minkik715.membership.domain.MembershipId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class FindMembershipController(
    private val findMembershipUseCase: FindMembershipUseCase
) {

    @GetMapping("/memberships/{membershipId}")
    fun getMembershipByMemeberId(@PathVariable("membershipId") membershipId: Long): ResponseEntity<Membership> {
        return ResponseEntity.ok(findMembershipUseCase.getMembershipByMembershipId(FindMembershipQuery(membershipId)))
    }
}