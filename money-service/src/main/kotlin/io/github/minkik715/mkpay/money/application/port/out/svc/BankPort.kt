package io.github.minkik715.mkpay.money.application.port.out.svc

interface BankPort {
    fun getAccountValidByMemberId(membershipId: Long): BankValidResponse?
    fun getRegisteredBankAccount(membershipId: Long): RegisteredBankAccountAggregate?
}