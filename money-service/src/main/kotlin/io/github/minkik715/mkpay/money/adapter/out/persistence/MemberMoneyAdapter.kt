package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.money.application.port.out.MemberMoneyPort
import io.github.minkik715.mkpay.money.domain.*

@PersistenceAdapter
class MemberMoneyAdapter(
    private val memberMoneyJpaRepository: SpringDataMemberMoneyRepository
): MemberMoneyPort {
    override fun createMemberMoney(membershipId: MembershipId,  linkedBankAccount: LinkedBankAccount, aggregateIdentifier: MoneyAggregateIdentifier): MemberMoney {
        return memberMoneyJpaRepository.save(
            MemberMoneyJpaEntity(membershipId.membershipId, 0, linkedBankAccount.linkedBankAccount, aggregateIdentifier.aggregateIdentifier)).toDomain()
    }

    override fun increaseMemberMoney(
        membershipId: MembershipId,
        balance: Balance,
        linkedBankAccount: LinkedBankAccount,
    ): MemberMoney {
        val memberMoneyJpaEntity = memberMoneyJpaRepository.findByMembershipId(membershipId.membershipId)?.let {
            it.increaseBalance(balance.balance)
            memberMoneyJpaRepository.save(it)
        } ?: memberMoneyJpaRepository.save(MemberMoneyJpaEntity(membershipId.membershipId, balance.balance, linkedBankAccount.linkedBankAccount))

        return memberMoneyJpaEntity.toDomain()
    }

    override fun getMoneyByMembershipId(membershipId: TargetMembershipId): MemberMoney? {
       return memberMoneyJpaRepository.findByMembershipId(membershipId.targetMembershipId)?.toDomain()
    }

    override fun getMoneySumByMembershipIds(membershipId: List<TargetMembershipId>): Long {
        val findMoneySumByMembershipIds =
            memberMoneyJpaRepository.findMoneySumByMembershipIds(membershipId.map { it.targetMembershipId })
        println(findMoneySumByMembershipIds)
        return findMoneySumByMembershipIds
    }
}
