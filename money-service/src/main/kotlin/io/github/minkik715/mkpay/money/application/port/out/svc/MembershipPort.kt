package io.github.minkik715.mkpay.money.application.port.out.svc

interface MembershipPort {
    fun getMembershipByMemberId(membershipId: Long): MembershipValidResponse?
}