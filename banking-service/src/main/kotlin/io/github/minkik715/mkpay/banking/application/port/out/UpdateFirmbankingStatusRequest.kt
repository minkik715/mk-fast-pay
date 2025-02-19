package io.github.minkik715.mkpay.banking.application.port.out

import io.github.minkik715.mkpay.banking.domain.FirmbankingRequestId
import io.github.minkik715.mkpay.banking.domain.FirmbankingStatus

data class UpdateFirmbankingStatusRequest(
    val firmbankingRequestId: FirmbankingRequestId,
    val firmbankingStatus: FirmbankingStatus,
    val uuid: String,
)