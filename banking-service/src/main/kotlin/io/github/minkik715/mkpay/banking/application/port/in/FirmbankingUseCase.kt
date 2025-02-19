package io.github.minkik715.mkpay.banking.application.port.`in`

import io.github.minkik715.mkpay.banking.domain.FirmbankingRequest

interface FirmbankingUseCase {
    fun requestFirmbanking(command: FirmbankingRequestCommand): FirmbankingRequest?
    //fun getBankAccounts(command: FindBankAccountsCommand): List<BankAccount>
}