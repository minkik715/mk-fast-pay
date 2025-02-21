package io.github.minkik715.mkpay.common.feign.membership

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient (name="membership-service", url = "http://membership-service:8080")
@Component
interface MembershipFeign {
    @GetMapping("/memberships/{membershipId}")
    fun getMembershipByMemberId(@PathVariable("membershipId") membershipId: Long): ResponseEntity<MembershipFeignResponse>
}