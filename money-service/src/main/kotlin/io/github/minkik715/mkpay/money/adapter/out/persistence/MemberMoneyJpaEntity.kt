package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.money.domain.*
import jakarta.persistence.*


@Entity
@Table(name = "member_money")
class MemberMoneyJpaEntity(
    private val membershipId: Long,

    private var balance: Int,

    private var linkedBankAccount: Boolean,

    private var aggregateIdentifier: String =""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var memberMoneyId: Long = 0

    fun toDomain(): MemberMoney {
        return MemberMoney.generateMemberMoney(
            MemberMoneyId(memberMoneyId),
            MembershipId(membershipId),
            Balance(balance),
            LinkedBankAccount(linkedBankAccount),
            MoneyAggregateIdentifier(
                this.aggregateIdentifier
            )
        )
    }

    fun increaseBalance(balance: Int) {
        this.balance += balance
    }
}