package io.github.minkik715.membership.adapater.out.persistence

import io.github.minkik715.common.PersistenceAdapter
import io.github.minkik715.membership.adapater.`in`.web.FindMembershipRequest
import io.github.minkik715.membership.application.port.out.FindMembershipPort
import io.github.minkik715.membership.application.port.out.RegisterMembershipPort
import io.github.minkik715.membership.domain.*
import org.springframework.data.crossstore.ChangeSetPersister

@PersistenceAdapter
class MembershipPersistenceAdapter(
    private val membershipRepository: SpringDataMembershipRepository
): RegisterMembershipPort, FindMembershipPort {

    override fun createMembership(
        membershipName: MembershipName,
        membershipAddress: MembershipAddress,
        membershipEmail: MembershipEmail,
        membershipIsValid: MembershipIsValid,
        membershipIsCorp: MembershipIsCorp
    ): MembershipJpaEntity {
        return membershipRepository.save(
            MembershipJpaEntity(
                name = membershipName.name,
                address = membershipAddress.address,
                email = membershipEmail.email,
                isValid = membershipIsValid.isValid,
                isCorp = membershipIsCorp.isCorp
            )
        )
    }

    override fun getMembershipByMembershipId(membershipId: MembershipId): MembershipJpaEntity {
        return membershipRepository.getById(membershipId.membershipId) ?: throw ChangeSetPersister.NotFoundException()
    }
}