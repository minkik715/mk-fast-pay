package io.github.minkik715.mkpay.banking.application.port.`in`

import io.github.minkik715.mkpay.banking.domain.BankAccount

interface BankAccountUseCase {
    fun createRegisteredBankAccount(command: RegisterAccountCommand): BankAccount?
    fun getBankAccounts(command: FindBankAccountsCommand): List<BankAccount>?
}