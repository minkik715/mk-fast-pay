package io.github.minkik715.mkpay.money.aggregation.application.port.`in`

interface GetMoneySumUseCase {
    fun getMoneySumByAddress(addressName: GetMoneySumAddressCommand): Long
}