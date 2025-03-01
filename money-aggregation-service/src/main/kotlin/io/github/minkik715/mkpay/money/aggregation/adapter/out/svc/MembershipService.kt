package io.github.minkik715.mkpay.money.aggregation.adapter.out.svc

import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.membership.MembershipFeign
import io.github.minkik715.mkpay.money.aggregation.application.port.out.svc.MembershipPort

@ServiceAdapter
class MembershipService(
    private val membershipFeign: MembershipFeign,
): MembershipPort {
    override fun getMemberIdsByAddress(addressName: String): Set<Long> {
        return membershipFeign.getMembershipByAddressName(addressName).body?.map { it.membershipId }?.toSet() ?: emptySet()
    }
}