package io.github.minkik715.mkpay.money.application.port.`in`

import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest

interface MoneyUseCase {
    fun requestIncreaseMoney(command: IncreaseMoneyCommand): MoneyChangingRequest
}