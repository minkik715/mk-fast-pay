package io.github.minkik715.mkpay.banking.adapter.out.svc

import io.github.minkik715.mkpay.banking.application.port.out.svc.membership.MembershipPort
import io.github.minkik715.mkpay.banking.application.port.out.svc.membership.MembershipStatus
import io.github.minkik715.mkpay.common.ServiceAdapter
import io.github.minkik715.mkpay.common.feign.membership.MembershipFeign

@ServiceAdapter
class MembershipServiceAdapter(
    private val membershipFeign: MembershipFeign
): MembershipPort {
    override fun getMembership(membershipId: Long): MembershipStatus? {

        kotlin.runCatching {
            val result = membershipFeign.getMembershipByMemberId(membershipId)
            if(result.statusCode.is2xxSuccessful){
                val membership = result.body!!
                if(membership.isValid){
                    return MembershipStatus(membershipId, true)
                }else{
                    return MembershipStatus(membershipId, false)
                }
            }else{
                return null
            }
        }.onFailure {
            throw it
        }
        return null
    }
}