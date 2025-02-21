package io.github.minkik715.mkpay.banking.application.port.out.svc.membership

import io.github.minkik715.mkpay.common.feign.membership.MembershipFeignResponse

interface MembershipPort {
    fun getMembership(membershipId: Long): MembershipStatus?
}