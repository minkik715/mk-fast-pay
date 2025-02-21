package io.github.minkik715.mkpay.banking.application.service

import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.banking.application.port.`in`.FirmbankingRequestCommand
import io.github.minkik715.mkpay.banking.application.port.`in`.FirmbankingUseCase
import io.github.minkik715.mkpay.banking.application.port.out.BankExternalPort
import io.github.minkik715.mkpay.banking.application.port.out.FirmBankingPort
import io.github.minkik715.mkpay.banking.application.port.out.FirmbankingExternalRequest
import io.github.minkik715.mkpay.banking.application.port.out.UpdateFirmbankingStatusRequest
import io.github.minkik715.mkpay.banking.domain.*
import jakarta.transaction.Transactional
import java.util.*

@UseCase
class FirmbankingService(
    private val firmBankingPort: FirmBankingPort,
    private val firmbankingExternalPort: BankExternalPort
): FirmbankingUseCase {

    @Transactional
    override fun requestFirmbanking(command: FirmbankingRequestCommand): FirmbankingRequest {
        //Business Logic
        // a -> b

        //1. 요청에 대한 정보 먼저 write  요청 상태로
        val requestEntity = firmBankingPort.createFirmBankingRequest(
            FromBankName(command.fromBankName),
            FromBankAccountNumber(command.fromBankAccountNumber),
            ToBankName(command.toBankName),
            ToBankAccountNumber(command.toBankAccountNumber),
            MoneyAmount(command.moneyAmount),
            FirmbankingStatus(0)
        )


        // 2. 외부 은행 펌뱅킹 요청
        val externalFirmbankingResult = firmbankingExternalPort.requestExternalFirmbanking(FirmbankingExternalRequest(
            command.fromBankName,
            command.fromBankAccountNumber,
            command.toBankName,
            command.toBankAccountNumber,
            command.moneyAmount
        ))

        var uuid: String = UUID.randomUUID().toString()

        //3. 결과에 따라 다르게
        var externalResultValue = if(externalFirmbankingResult.resultCode ==0){
            1
        }else{
            2
        }

        //4. 결과를 리턴
        return firmBankingPort.updateFirmbankingStatus(
            UpdateFirmbankingStatusRequest(
                FirmbankingRequestId(requestEntity.firmbankingRequestId),
                FirmbankingStatus(externalResultValue),
                uuid
            )
        )
    }
}