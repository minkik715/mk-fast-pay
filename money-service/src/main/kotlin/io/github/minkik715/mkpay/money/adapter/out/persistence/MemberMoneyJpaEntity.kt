package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.money.domain.*
import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest.ChangingMoneyStatus
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*


@Entity
@Table(name = "member_money")
class MemberMoneyJpaEntity(
    private val membershipId: Long,

    private var balance: Int,

    private var linkedBankAccount: Boolean
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
        )
    }

    fun updateBalance(balance: Int) {
        this.balance += balance
    }
}