package io.github.minkik715.mkpay.payment.application.port.out.svc

interface MembershipPort {
    fun getMembershipByMemberId(membershipId: Long): MembershipValidResponse?
}