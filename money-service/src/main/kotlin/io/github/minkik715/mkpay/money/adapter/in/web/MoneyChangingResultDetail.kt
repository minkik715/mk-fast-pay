package io.github.minkik715.mkpay.money.adapter.`in`.web

import io.github.minkik715.mkpay.money.domain.MoneyChangingRequest


data class MoneyChangingResultDetail(
    val moneyChangingRequestId: Long,

    val changingType: MoneyChangingType,
    val amount: Int,

    val resultCode: MoneyChangingResultStatus
){
    constructor(moneyChangingRequest: MoneyChangingRequest) : this(
        moneyChangingRequestId = moneyChangingRequest.moneyChangingRequestId,
        changingType = MoneyChangingType.valueOf(moneyChangingRequest.changingType.name),
        amount = moneyChangingRequest.changingMoneyAmount,
        resultCode = MoneyChangingResultStatus.SUCCEED
    )

    enum class MoneyChangingType{
        INCREASING,
        DECREASING
    }

    enum class MoneyChangingResultStatus{
        SUCCEED,
        FAILED,

        FAILED_NOT_ENOUGH_MONEY,
        FAILED_NOT_EXISTS_MEMBERSHIP,
        FAILED_NOT_EXISTS_MONEY_CHANGING_REQUEST
    }
}


