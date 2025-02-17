package io.github.minkik715.membership.application.service

import io.github.minkik715.common.UseCase
import io.github.minkik715.membership.application.port.`in`.FindMembershipQuery
import io.github.minkik715.membership.application.port.`in`.FindMembershipUseCase
import io.github.minkik715.membership.application.port.out.FindMembershipPort
import io.github.minkik715.membership.domain.Membership
import io.github.minkik715.membership.domain.MembershipId

@UseCase
class FindMembershipService(
    private val findMembershipPort: FindMembershipPort
): FindMembershipUseCase {

    override fun getMembershipByMembershipId(query: FindMembershipQuery): Membership {
        return findMembershipPort.getMembershipByMembershipId(MembershipId(query.membershipId)).toDomain()
    }
}