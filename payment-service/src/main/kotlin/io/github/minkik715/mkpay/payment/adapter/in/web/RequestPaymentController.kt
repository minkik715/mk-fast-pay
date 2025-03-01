package io.github.minkik715.mkpay.payment.adapter.`in`.web

import io.github.minkik715.mkpay.common.WebAdapter
import io.github.minkik715.mkpay.payment.application.port.`in`.PaymentUseCase
import io.github.minkik715.mkpay.payment.application.port.`in`.RequestPaymentCommand
import io.github.minkik715.mkpay.payment.domain.Payment
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class RequestPaymentController(
    private val paymentUseCase: PaymentUseCase
) {

    @PostMapping("payment")
    fun requestPayment(@RequestBody request: PaymentRequest): Payment {
        return paymentUseCase.createPayment(
            RequestPaymentCommand(
                membershipId = request.membershipId,
                price = request.price,
                franchiseId = request.franchiseId,
                franchiseFeeRate = request.franchiseFeeRate,
            )
        )
    }

}