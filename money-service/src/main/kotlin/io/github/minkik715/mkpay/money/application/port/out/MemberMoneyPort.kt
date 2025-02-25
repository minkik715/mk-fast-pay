package io.github.minkik715.mkpay.money.application.port.out

import io.github.minkik715.mkpay.money.domain.*

interface MemberMoneyPort {

    fun createMemberMoney(
        membershipId: MembershipId,
        linkedBankAccount: LinkedBankAccount,
        aggregateIdentifier: MoneyAggregateIdentifier
    ): MemberMoney

    fun increaseMemberMoney(
        membershipId: MembershipId,
        amount: Balance,
        linkedBankAccount: LinkedBankAccount,
    ): MemberMoney

    fun getMoneyByMembershipId(membershipId: TargetMembershipId): MemberMoney?

}