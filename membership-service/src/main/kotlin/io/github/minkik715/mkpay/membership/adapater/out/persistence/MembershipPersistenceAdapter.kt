package io.github.minkik715.mkpay.membership.adapater.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.membership.application.port.out.MembershipOutPort
import io.github.minkik715.mkpay.membership.domain.*
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
            membershipRepository.findByMembershipId(membershipId.membershipId)
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
        return membershipRepository.findByMembershipId(membershipId.membershipId) ?: throw ChangeSetPersister.NotFoundException()
    }

    override fun getMembershipByAddress(address: MembershipAddress): Set<Membership> {
        return membershipRepository.findByAddress(address.address).map { it.toDomain() }.toSet()
    }
}