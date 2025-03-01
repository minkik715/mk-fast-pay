package io.github.minkik715.mkpay.membership.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.membership.application.port.`in`.*
import io.github.minkik715.mkpay.membership.application.port.out.MembershipOutPort
import io.github.minkik715.mkpay.membership.domain.*

@UseCase
class MembershipService(
    private val membershipOutPort: MembershipOutPort
): MembershipUseCase {
    override fun getMembershipByMembershipId(cmd: FindMembershipCommand): Membership {
        return membershipOutPort.getMembershipByMembershipId(
            MembershipId(
                cmd.membershipId
            )
        ).toDomain()
    }

    override fun registerMembership(command: RegisterMembershipCommand): Membership {
        // command -> DB
        return membershipOutPort.createMembership(
            MembershipName(command.name),
            MembershipAddress(command.address),
            MembershipEmail(command.email),
            MembershipIsValid(command.isValid),
            MembershipIsCorp(command.isCorp)
        ).toDomain()
        // biz logic -> DB
        // external system
        // port, adapter
    }

    override fun modifyMembership(command: ModifyMembershipCommand): Membership {
        return membershipOutPort.modifyMembership(
            MembershipId(command.id),
            MembershipName(command.name),
            MembershipAddress(command.address),
            MembershipEmail(command.email),
            MembershipIsValid(command.isValid),
            MembershipIsCorp(command.isCorp)
        ).toDomain()
    }

    override fun getMembershipByAddress(cmd: FindMembershipByAddressCommand): Set<Membership> {
        return membershipOutPort.getMembershipByAddress(MembershipAddress(cmd.addressName))
    }
}