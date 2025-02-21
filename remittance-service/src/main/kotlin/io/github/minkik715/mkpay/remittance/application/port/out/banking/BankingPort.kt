package io.github.minkik715.mkpay.remittance.application.port.out.banking

interface BankingPort {
    fun getMembershipBankingInfo(bankName: String, bankAccountNumber: String): BankingInfo?

    fun requestFirmbanking(bankName: String, bankAccountNumber: String, amount: Int): Boolean
}