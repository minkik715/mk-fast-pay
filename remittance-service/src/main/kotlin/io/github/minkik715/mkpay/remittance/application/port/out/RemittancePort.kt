package io.github.minkik715.mkpay.remittance.application.port.out

import io.github.minkik715.mkpay.remittance.domain.*


interface RemittancePort {
    fun createRemittanceRequestHistory(
        fromMembershipId: FromMembershipId,
        toMembershipId: ToMembershipId,
        toBankName: ToBankName,
        toBankAccountNumber: ToBankAccountNumber,
        remittanceType: RemittanceType,
        amount: Amount,
        remittanceStatus: RemittanceStatus,
    ): RemittanceRequest

    fun updateRemittanceStatus(remittanceRequestId: RemittanceRequestId, status: RemittanceStatus): RemittanceRequest
}