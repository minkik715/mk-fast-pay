package io.github.minkik715.mkpay.membership.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.membership.application.port.`in`.FindMembershipCommand
import io.github.minkik715.mkpay.membership.application.port.`in`.MembershipUseCase
import io.github.minkik715.mkpay.membership.application.port.`in`.ModifyMembershipCommand
import io.github.minkik715.mkpay.membership.application.port.`in`.RegisterMembershipCommand
import io.github.minkik715.mkpay.membership.application.port.out.MembershipOutPort
import io.github.minkik715.membership.domain.*

@UseCase
class MembershipService(
    private val membershipOutPort: io.github.minkik715.mkpay.membership.application.port.out.MembershipOutPort
): io.github.minkik715.mkpay.membership.application.port.`in`.MembershipUseCase {
    override fun getMembershipByMembershipId(query: io.github.minkik715.mkpay.membership.application.port.`in`.FindMembershipCommand): io.github.minkik715.mkpay.membership.domain.Membership {
        return membershipOutPort.getMembershipByMembershipId(
            io.github.minkik715.mkpay.membership.domain.MembershipId(
                query.membershipId
            )
        ).toDomain()
    }

    override fun registerMembership(command: io.github.minkik715.mkpay.membership.application.port.`in`.RegisterMembershipCommand): io.github.minkik715.mkpay.membership.domain.Membership {
        // command -> DB
        return membershipOutPort.createMembership(
            io.github.minkik715.mkpay.membership.domain.MembershipName(command.name),
            io.github.minkik715.mkpay.membership.domain.MembershipAddress(command.address),
            io.github.minkik715.mkpay.membership.domain.MembershipEmail(command.email),
            io.github.minkik715.mkpay.membership.domain.MembershipIsValid(command.isValid),
            io.github.minkik715.mkpay.membership.domain.MembershipIsCorp(command.isCorp)
        ).toDomain()
        // biz logic -> DB
        // external system
        // port, adapter
    }

    override fun modifyMembership(command: io.github.minkik715.mkpay.membership.application.port.`in`.ModifyMembershipCommand): io.github.minkik715.mkpay.membership.domain.Membership {
        return membershipOutPort.modifyMembership(
            io.github.minkik715.mkpay.membership.domain.MembershipId(command.id),
            io.github.minkik715.mkpay.membership.domain.MembershipName(command.name),
            io.github.minkik715.mkpay.membership.domain.MembershipAddress(command.address),
            io.github.minkik715.mkpay.membership.domain.MembershipEmail(command.email),
            io.github.minkik715.mkpay.membership.domain.MembershipIsValid(command.isValid),
            io.github.minkik715.mkpay.membership.domain.MembershipIsCorp(command.isCorp)
        ).toDomain()
    }
}