package io.github.minkik715.mkpay.remittance.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.membership.MembershipFeign
import io.github.minkik715.mkpay.remittance.application.port.out.membership.MembershipPort
import io.github.minkik715.mkpay.remittance.application.port.out.membership.MembershipStatus

@ServiceAdapter
class MembershipServiceAdapter(
    private val membershipFeign: MembershipFeign
): MembershipPort {
    override fun getMembershipStatus(membershipId: Long): MembershipStatus {
        membershipFeign.getMembershipByMemberId(membershipId).let {
            if(it.body?.isValid != true) throw IllegalArgumentException("IncreaseMoney command cannot be invalid")
        }
        return MembershipStatus(membershipId, true)
    }
}