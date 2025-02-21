package io.github.minkik715.mkpay.membership.application.port.`in`

import io.github.minkik715.mkpay.membership.domain.Membership

interface MembershipUseCase {
    fun getMembershipByMembershipId(query: io.github.minkik715.mkpay.membership.application.port.`in`.FindMembershipCommand): io.github.minkik715.mkpay.membership.domain.Membership

    fun registerMembership(command: io.github.minkik715.mkpay.membership.application.port.`in`.RegisterMembershipCommand): io.github.minkik715.mkpay.membership.domain.Membership

    fun modifyMembership(command: io.github.minkik715.mkpay.membership.application.port.`in`.ModifyMembershipCommand): io.github.minkik715.mkpay.membership.domain.Membership

}