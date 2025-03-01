package io.github.minkik715.mkpay.money.application.port.`in`

import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest

interface MoneyUseCase {
    fun requestIncreaseMoney(command: IncreaseMoneyCommand): MoneyChangingRequest
    fun getMoneySumByMembershipIds(command: GetMembershipsMoneySumCommand): Long

    fun requestIncreaseMoneyAsync(command: IncreaseMoneyCommand): MoneyChangingRequest

    fun requestIncreaseMoneyByEvent(command: IncreaseMoneyCommand)

    fun requestCreateMemberMoney(command: CreateMemberMoneyCommand)
}