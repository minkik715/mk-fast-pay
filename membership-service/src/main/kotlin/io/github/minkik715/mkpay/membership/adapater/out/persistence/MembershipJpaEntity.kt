package io.github.minkik715.mkpay.membership.adapater.out.persistence

import io.github.minkik715.membership.domain.*
import jakarta.persistence.*


@Entity
@Table(name = "membership")
class MembershipJpaEntity(
    private var name: String,

    private var address: String,

    private var email: String,

    private var isValid: Boolean,

    private var isCorp: Boolean,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var membershipId: Long = 0

    fun toDomain(): io.github.minkik715.mkpay.membership.domain.Membership {
        return io.github.minkik715.mkpay.membership.domain.Membership.generateMember(
            io.github.minkik715.mkpay.membership.domain.MembershipId(this.membershipId),
            io.github.minkik715.mkpay.membership.domain.MembershipName(this.name),
            io.github.minkik715.mkpay.membership.domain.MembershipEmail(this.email),
            io.github.minkik715.mkpay.membership.domain.MembershipAddress(this.address),
            io.github.minkik715.mkpay.membership.domain.MembershipIsValid(this.isValid),
            io.github.minkik715.mkpay.membership.domain.MembershipIsCorp(this.isCorp)
        )
    }

    fun modify(
        membershipName: io.github.minkik715.mkpay.membership.domain.MembershipName,
        membershipAddress: io.github.minkik715.mkpay.membership.domain.MembershipAddress,
        membershipEmail: io.github.minkik715.mkpay.membership.domain.MembershipEmail,
        membershipIsValid: io.github.minkik715.mkpay.membership.domain.MembershipIsValid,
        membershipIsCorp: io.github.minkik715.mkpay.membership.domain.MembershipIsCorp
    ): io.github.minkik715.mkpay.membership.adapater.out.persistence.MembershipJpaEntity {
        this.name = membershipName.name
        this.address = membershipAddress.address
        this.email = membershipEmail.email
        this.isValid = membershipIsValid.isValid
        this.isCorp = membershipIsCorp.isCorp
        return this
    }
}
