package io.github.minkik715.membership.adapater.out.persistence

import io.github.minkik715.common.PersistenceAdapter
import io.github.minkik715.membership.application.port.out.MembershipOutPort
import io.github.minkik715.membership.domain.*
import org.springframework.data.crossstore.ChangeSetPersister

@PersistenceAdapter
class MembershipPersistenceAdapter(
    private val membershipRepository: SpringDataMembershipRepository
): MembershipOutPort {

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

    override fun modifyMembership(
        membershipId: MembershipId,
        membershipName: MembershipName,
        membershipAddress: MembershipAddress,
        membershipEmail: MembershipEmail,
        membershipIsValid: MembershipIsValid,
        membershipIsCorp: MembershipIsCorp
    ): MembershipJpaEntity {
        val entity =
            membershipRepository.getById(membershipId.membershipId) ?: throw ChangeSetPersister.NotFoundException()

        val modify = entity.modify(
            membershipName,
            membershipAddress,
            membershipEmail,
            membershipIsValid,
            membershipIsCorp
        )

        return membershipRepository.save(modify)
    }


    override fun getMembershipByMembershipId(membershipId: MembershipId): MembershipJpaEntity {
        return membershipRepository.getById(membershipId.membershipId) ?: throw ChangeSetPersister.NotFoundException()
    }
}