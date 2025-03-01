package io.github.minkik715.mkpay.payment.application.port.`in`

import io.github.minkik715.mkpay.payment.domain.Payment

interface PaymentUseCase {
    fun createPayment(command: RequestPaymentCommand): Payment
}