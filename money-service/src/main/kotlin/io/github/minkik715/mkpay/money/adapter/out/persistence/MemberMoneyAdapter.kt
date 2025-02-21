package io.github.minkik715.mkpay.money.adapter.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.money.application.port.out.MemberMoneyPort
import io.github.minkik715.mkpay.money.domain.*

@PersistenceAdapter
class MemberMoneyAdapter(
    private val memberMoneyJpaRepository: SpringDataMemberMoneyRepository
): MemberMoneyPort {

    override fun increaseMemberMoney(
        membershipId: MembershipId,
        balance: Balance,
        linkedBankAccount: LinkedBankAccount
    ): MemberMoney {
        val memberMoneyJpaEntity = memberMoneyJpaRepository.findByMembershipId(membershipId.membershipId)?.let {
            it.updateBalance(balance.balance)
            memberMoneyJpaRepository.save(it)
        } ?: memberMoneyJpaRepository.save(MemberMoneyJpaEntity(membershipId.membershipId, balance.balance, linkedBankAccount.linkedBankAccount))

        return memberMoneyJpaEntity.toDomain()
    }
}
