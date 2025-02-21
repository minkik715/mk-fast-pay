package io.github.minkik715.mkpay.remittance.application.port.`in`

import io.github.minkik715.mkpay.remittance.domain.RemittanceRequest


interface RemittanceUseCase {
    fun requestRemittance(command: RequestRemittanceCommand): RemittanceRequest
}