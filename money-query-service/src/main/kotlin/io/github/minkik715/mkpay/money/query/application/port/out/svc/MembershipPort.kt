package io.github.minkik715.mkpay.money.query.application.port.out.svc

interface MembershipPort {
    fun getMembershipAddressByMemberId(membershipId: Long): MembershipAddressResponse?
}