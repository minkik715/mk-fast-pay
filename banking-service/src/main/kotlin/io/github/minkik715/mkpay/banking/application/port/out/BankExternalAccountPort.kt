package io.github.minkik715.mkpay.banking.application.port.out

import io.github.minkik715.mkpay.banking.adapter.out.external.bank.GetBankAccountRequest
import io.github.minkik715.mkpay.banking.adapter.out.external.bank.GetBankAccountResponse

interface BankExternalAccountPort {
    fun getBankAccountInfo(request: GetBankAccountRequest): GetBankAccountResponse
}