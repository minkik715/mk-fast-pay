package io.github.minkik715.io.github.minkik715.membership.domain

import org.springframework.beans.factory.annotation.Value

// 오염이 되면 안되는 클래스, 고객 정보, 핵심 도메인
//각 필드의 생성을 value Object 로 처리함으로 데이터 무결성 및 실수를 방지
// 각 필드에 타입을 명시적으로 적음으로서 Builder와 달리 값을 넣을때도 value class를 사용해야하므로 더 안전함 ( 오류 방지!!!)
// 안전한 시스템 강제
class Membership private constructor(
    val membershipId: String,
    val name: String,
    val email: String,
    val address: String,
    val isValid: Boolean,
    val isCorp: Boolean,
){
    companion object {
        // MemberShip 을 만들기 위해선 반드시 필요
        fun generateMember(
            membershipId: MembershipId,
            name: MembershipName,
            email: MembershipEmail,
            address: MembershipAddress,
            isValid: MembershipIsValid,
            isCorp: MembershipIsCorp,
        ): Membership {
            return Membership(
                membershipId = membershipId.membershipId,
                name = name.name,
                email = email.email,
                address = address.address,
                isValid = isValid.isValid,
                isCorp = isCorp.isCorp,

            )
        }
    }
}

@JvmInline
value class MembershipId private constructor(val membershipId: String)
@JvmInline
value class MembershipName private constructor(val name: String)
@JvmInline
value class MembershipEmail private constructor(val email: String)
@JvmInline
value class MembershipAddress private constructor(val address: String)
@JvmInline
value class MembershipIsValid private constructor(val isValid: Boolean)
@JvmInline
value class MembershipIsCorp private constructor(val isCorp: Boolean)



