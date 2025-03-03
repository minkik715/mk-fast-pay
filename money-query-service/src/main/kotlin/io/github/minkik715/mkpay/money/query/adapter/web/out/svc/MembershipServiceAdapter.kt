package io.github.minkik715.mkpay.money.query.adapter.web.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.membership.MembershipFeign
import io.github.minkik715.mkpay.money.query.application.port.out.svc.MembershipPort
import io.github.minkik715.mkpay.money.query.application.port.out.svc.MembershipAddressResponse

@ServiceAdapter
class MembershipServiceAdapter(
    private val membershipFeign: MembershipFeign
): MembershipPort {
    override fun getMembershipAddressByMemberId(membershipId: Long): MembershipAddressResponse? {
        return membershipFeign.getMembershipByMemberId(membershipId).body?.let {
            MembershipAddressResponse(it.membershipId, it.address)
        }
    }
}