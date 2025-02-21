package io.github.minkik715.mkpay.membership.adapater.out.persistence

import io.github.minkik715.mkpay.membership.domain.*
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

    fun toDomain(): Membership {
        return Membership.generateMember(
            membershipId = MembershipId(this.membershipId),
            name = MembershipName(this.name),
            email = MembershipEmail(this.email),
            address = MembershipAddress(this.address),
            isValid = MembershipIsValid(this.isValid),
            isCorp = MembershipIsCorp(this.isCorp)
        )
    }

    fun modify(
        membershipName: MembershipName,
        membershipAddress: MembershipAddress,
        membershipEmail: MembershipEmail,
        membershipIsValid: MembershipIsValid,
        membershipIsCorp: MembershipIsCorp
    ): MembershipJpaEntity {
        this.name = membershipName.name
        this.address = membershipAddress.address
        this.email = membershipEmail.email
        this.isValid = membershipIsValid.isValid
        this.isCorp = membershipIsCorp.isCorp
        return this
    }
}
