package io.github.minkik715.mkpay.membership.adapater.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.membership.application.port.out.MembershipOutPort
import io.github.minkik715.membership.domain.*
import org.springframework.data.crossstore.ChangeSetPersister

@PersistenceAdapter
class MembershipPersistenceAdapter(
    private val membershipRepository: io.github.minkik715.mkpay.membership.adapater.out.persistence.SpringDataMembershipRepository
): io.github.minkik715.mkpay.membership.application.port.out.MembershipOutPort {

    override fun createMembership(
        membershipName: io.github.minkik715.mkpay.membership.domain.MembershipName,
        membershipAddress: io.github.minkik715.mkpay.membership.domain.MembershipAddress,
        membershipEmail: io.github.minkik715.mkpay.membership.domain.MembershipEmail,
        membershipIsValid: io.github.minkik715.mkpay.membership.domain.MembershipIsValid,
        membershipIsCorp: io.github.minkik715.mkpay.membership.domain.MembershipIsCorp
    ): io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity {
        return membershipRepository.save(
            io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity(
                name = membershipName.name,
                address = membershipAddress.address,
                email = membershipEmail.email,
                isValid = membershipIsValid.isValid,
                isCorp = membershipIsCorp.isCorp
            )
        )
    }

    override fun modifyMembership(
        membershipId: io.github.minkik715.mkpay.membership.domain.MembershipId,
        membershipName: io.github.minkik715.mkpay.membership.domain.MembershipName,
        membershipAddress: io.github.minkik715.mkpay.membership.domain.MembershipAddress,
        membershipEmail: io.github.minkik715.mkpay.membership.domain.MembershipEmail,
        membershipIsValid: io.github.minkik715.mkpay.membership.domain.MembershipIsValid,
        membershipIsCorp: io.github.minkik715.mkpay.membership.domain.MembershipIsCorp
    ): io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity {
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


    override fun getMembershipByMembershipId(membershipId: io.github.minkik715.mkpay.membership.domain.MembershipId): io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity {
        return membershipRepository.getById(membershipId.membershipId) ?: throw ChangeSetPersister.NotFoundException()
    }
}