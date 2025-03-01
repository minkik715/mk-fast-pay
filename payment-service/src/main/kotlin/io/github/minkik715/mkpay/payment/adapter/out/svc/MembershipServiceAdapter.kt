package io.github.minkik715.mkpay.payment.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.membership.MembershipFeign
import io.github.minkik715.mkpay.payment.application.port.out.svc.MembershipPort
import io.github.minkik715.mkpay.payment.application.port.out.svc.MembershipValidResponse

@ServiceAdapter
class MembershipServiceAdapter(
    private val membershipFeign: MembershipFeign
): MembershipPort {
    override fun getMembershipByMemberId(membershipId: Long): MembershipValidResponse? {
        return membershipFeign.getMembershipByMemberId(membershipId).body?.let {
            MembershipValidResponse(it.membershipId, it.isValid)
        }
    }
}