package io.github.minkik715.mkpay.money.adapter.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.money.application.port.`in`.IncreaseMoneyCommand
import io.github.minkik715.mkpay.money.application.port.`in`.MoneyUseCase
import org.springframework.web.bind.annotation.*

@WebAdapter
@RestController
class MoneyController(
    private val moneyUseCase: MoneyUseCase
) {

    @PostMapping("/money/increase")
    fun increaseMoneyRequest(@RequestBody request: IncreaseMoneyRequest): MoneyChangingResultDetail {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = IncreaseMoneyCommand(request.targetMembershipId, request.amount)
        val requestIncreaseMoney = moneyUseCase.requestIncreaseMoneyAsync(command)

        return MoneyChangingResultDetail(requestIncreaseMoney)
    }

    @PostMapping("/money/decrease")
    fun decreaseMoneyRequest(@RequestBody request: IncreaseMoneyRequest): MoneyChangingResultDetail {
        // request -> Command (Requst 변화에 따른 Command/ UseCase 변화 최소화)
        val command = IncreaseMoneyCommand(request.targetMembershipId, request.amount)
        val requestIncreaseMoney = moneyUseCase.requestIncreaseMoney(command)

        return MoneyChangingResultDetail(requestIncreaseMoney)
    }


}