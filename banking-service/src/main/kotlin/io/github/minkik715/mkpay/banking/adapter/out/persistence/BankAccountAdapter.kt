package io.github.minkik715.mkpay.banking.adapter.out.persistence

import io.github.minkik715.mkpay.common.PersistenceAdapter
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.BankAccountPort
import io.github.minkik715.mkpay.banking.domain.*

@PersistenceAdapter
class BankAccountAdapter(
    private val springDataRegisteredBankAccountRepository: SpringDataBankAccountRepository
): BankAccountPort {
    override fun createRegisteredBankAccount(
        membershipId: MembershipId,
        bankName: BankName,
        bankAccountNumber: BankAccountNumber,
        linkedStatusIsValid: LinkedStatusIstValid
    ): BankAccount {
        return springDataRegisteredBankAccountRepository.save(
            BankAccountJpaEntity(
                membershipId = membershipId.membershipId,
                bankName = bankName.bankName,
                bankAccountNumber = bankAccountNumber.backAccountNumber,
                linkedStatusIsValid = linkedStatusIsValid.linkedStatusIsValid
            )
        ).toDomain()
    }

    override fun findBankAccounts(membershipId: MembershipId): List<BankAccount> {
        return springDataRegisteredBankAccountRepository.findAllByMembershipId(membershipId.membershipId).map { it.toDomain() }
    }
}
