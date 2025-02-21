package io.github.minkik715.mkpay.remittance.adapter.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.remittance.application.port.`in`.RemittanceUseCase
import io.github.minkik715.mkpay.remittance.application.port.`in`.RequestRemittanceCommand
import io.github.minkik715.mkpay.remittance.domain.RemittanceRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class RemittanceController(
    private val remittanceUseCase: RemittanceUseCase
) {

    @PostMapping("/remittance")
    fun requestRemittance(@RequestBody request: RequestRemittanceRequest): RemittanceRequest {

        val command = RequestRemittanceCommand(
            fromMembershipId = request.fromMembershipId,
            toMembershipId = request.toMembershipId,
            toBankName = request.toBankName,
            toBankAccountNumber = request.toBankAccountNumber,
            remittanceType = request.remittanceType,
            amount = request.amount,
        )

        return remittanceUseCase.requestRemittance(command)
    }

}