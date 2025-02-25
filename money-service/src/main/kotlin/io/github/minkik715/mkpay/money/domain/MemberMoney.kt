package io.github.minkik715.mkpay.money.domain


class MemberMoney private constructor(
    val memberMoneyId: Long,

    val membershipId: Long,

    val balance: Int,

    val linkedBankAccount: Boolean,

    val aggregateIdentifier: MoneyAggregateIdentifier

 ){

    companion object {
        fun generateMemberMoney(
            memberMoneyId: MemberMoneyId,
            membershipId: MembershipId,
            balance: Balance,
            linkedBankAccount: LinkedBankAccount,
            aggregateIdentifier: MoneyAggregateIdentifier
        ): MemberMoney {
            return MemberMoney(
                memberMoneyId = memberMoneyId.memberMoneyId,
                membershipId = membershipId.membershipId,
                balance = balance.balance,
                linkedBankAccount = linkedBankAccount.linkedBankAccount,
                aggregateIdentifier = aggregateIdentifier
            )
        }
    }

}

@JvmInline
value class MoneyAggregateIdentifier constructor(val aggregateIdentifier: String)

@JvmInline
value class MemberMoneyId constructor(val memberMoneyId: Long)
@JvmInline
value class MembershipId constructor(val membershipId: Long)
@JvmInline
value class Balance constructor(val balance: Int)
@JvmInline
value class LinkedBankAccount constructor(val linkedBankAccount: Boolean)
