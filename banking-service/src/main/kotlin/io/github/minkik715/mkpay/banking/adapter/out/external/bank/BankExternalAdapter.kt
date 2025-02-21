package io.github.minkik715.mkpay.banking.adapter.out.external.bank

import io.github.minkik715.mkpay.common.ExternalAdapter
import io.github.minkik715.mkpay.banking.application.port.out.external.BankExternalPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.GetBankAccountRequest
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.GetBankAccountResponse
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmbankingExternalRequest
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmbankingExternalResponse

@ExternalAdapter
class BankExternalAdapter: BankExternalPort {

    override fun getBankAccountInfo(request: GetBankAccountRequest): GetBankAccountResponse {

        return GetBankAccountResponse(request.bankName, request.bankAccountNumber, true)
    }

    override fun requestExternalFirmbanking(request: FirmbankingExternalRequest): FirmbankingExternalResponse {
        return FirmbankingExternalResponse(0)
    }
}