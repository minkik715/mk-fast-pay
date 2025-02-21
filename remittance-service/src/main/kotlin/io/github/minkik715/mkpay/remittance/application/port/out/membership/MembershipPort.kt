package io.github.minkik715.mkpay.remittance.application.port.out.membership

interface MembershipPort {
    fun getMembershipStatus(membershipId: Long): MembershipStatus
}