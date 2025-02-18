package io.github.minkik715.membership.application.service

import io.github.minkik715.common.UseCase
import io.github.minkik715.membership.application.port.`in`.FindMembershipCommand
import io.github.minkik715.membership.application.port.`in`.MembershipUseCase
import io.github.minkik715.membership.application.port.`in`.RegisterMembershipCommand
import io.github.minkik715.membership.application.port.out.MembershipOutPort
import io.github.minkik715.membership.domain.*

@UseCase
class MembershipService(
    private val membershipOutPort: MembershipOutPort
): MembershipUseCase {
    override fun getMembershipByMembershipId(query: FindMembershipCommand): Membership {
        return membershipOutPort.getMembershipByMembershipId(MembershipId(query.membershipId)).toDomain()
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
}