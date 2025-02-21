package io.github.minkik715.mkpay.banking.application.port.out.external

import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.GetBankAccountRequest
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.GetBankAccountResponse
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmbankingExternalRequest
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmbankingExternalResponse

interface BankExternalPort {
    fun getBankAccountInfo(request: GetBankAccountRequest): GetBankAccountResponse

    fun requestExternalFirmbanking(request: FirmbankingExternalRequest): FirmbankingExternalResponse
}