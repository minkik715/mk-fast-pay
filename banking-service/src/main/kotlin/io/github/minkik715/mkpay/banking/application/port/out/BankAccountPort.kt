package io.github.minkik715.mkpay.banking.application.port.out

import io.github.minkik715.mkpay.banking.domain.*

interface BankAccountPort {
    fun createRegisteredBankAccount(
        membershipId: MembershipId,
        bankName: BankName,
        bankAccountNumber: BankAccountNumber,
        linkedStatusIsValid: LinkedStatusIstValid
    ): BankAccount

    fun findBankAccounts(membershipId: MembershipId): List<BankAccount>
}