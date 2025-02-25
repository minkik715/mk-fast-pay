package io.github.minkik715.mkpay.banking.application.service

import io.github.minkik715.mkpay.banking.adapter.out.axon.command.RequestFirmbankingAxonCommand
import io.github.minkik715.mkpay.banking.adapter.out.axon.command.UpdateFirmbankingAxonCommand
import io.github.minkik715.mkpay.banking.adapter.out.axon.event.RequestFirmbankingAxonEvent
import io.github.minkik715.mkpay.banking.adapter.out.axon.event.UpdateFirmbankingAxonEvent
import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.banking.application.port.`in`.FirmbankingRequestCommand
import io.github.minkik715.mkpay.banking.application.port.`in`.FirmbankingUpdateCommand
import io.github.minkik715.mkpay.banking.application.port.`in`.FirmbankingUseCase
import io.github.minkik715.mkpay.banking.application.port.out.external.BankExternalPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmBankingPort
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.FirmbankingExternalRequest
import io.github.minkik715.mkpay.banking.application.port.out.persistence.firmbanking.UpdateFirmbankingStatusRequest
import io.github.minkik715.mkpay.banking.domain.*
import jakarta.transaction.Transactional
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import java.util.*

@UseCase
class FirmbankingService(
    private val firmBankingPort: FirmBankingPort,
    private val firmbankingExternalPort: BankExternalPort,
    private val commandGateway: CommandGateway
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
        val externalFirmbankingResult = firmbankingExternalPort.requestExternalFirmbanking(
            FirmbankingExternalRequest(
            command.fromBankName,
            command.fromBankAccountNumber,
            command.toBankName,
            command.toBankAccountNumber,
            command.moneyAmount
        )
        )

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

    //axon adapter 쪽으로 뺴야됨
    override fun requestFirmbankingByEvent(command: FirmbankingRequestCommand) {
        val aggregateIdentifier = UUID.randomUUID().toString()
        commandGateway.send<String>(RequestFirmbankingAxonCommand(
            aggregateIdentifier,
            command.fromBankName,
            command.fromBankAccountNumber,
            command.toBankName,
            command.toBankAccountNumber,
            command.moneyAmount,
            0
        )).exceptionally { throw it }
    }

    override fun updateFirmbankingByEvent(command: FirmbankingUpdateCommand) {
        firmBankingPort.getFirmbankingByRequestId(FirmbankingRequestId(command.firmbankingRequestId))?.let {
            commandGateway.send<String>(UpdateFirmbankingAxonCommand(it.aggregateIdentifier, it.firmbankingRequestId,  command.status)).exceptionally {
                throw it
            }
        }
    }
    @EventHandler
    fun on(event: UpdateFirmbankingAxonEvent) {
        firmBankingPort.updateFirmbankingStatus(UpdateFirmbankingStatusRequest(
            FirmbankingRequestId(event.firmbankingRequestId),
            FirmbankingStatus(event.firmbankingStatus),
            UUID.randomUUID().toString()
        ))
    }

    @EventHandler
    fun on(event: RequestFirmbankingAxonEvent){
        val aggregateIdentifier = event.aggregateIdentifier
        //1. 요청에 대한 정보 먼저 write  요청 상태로
        val requestEntity = firmBankingPort.createFirmBankingRequest(
            FromBankName(event.fromBankName),
            FromBankAccountNumber(event.fromBankAccountNumber),
            ToBankName(event.toBankName),
            ToBankAccountNumber(event.toBankAccountNumber),
            MoneyAmount(event.moneyAmount),
            FirmbankingStatus(0),
            FirmbankingRequestAggregateIdentifier(aggregateIdentifier)
        )


        // 2. 외부 은행 펌뱅킹 요청
        val externalFirmbankingResult = firmbankingExternalPort.requestExternalFirmbanking(
            FirmbankingExternalRequest(
                event.fromBankName,
                event.fromBankAccountNumber,
                event.toBankName,
                event.toBankAccountNumber,
                event.moneyAmount
            )
        )

        var uuid: String = UUID.randomUUID().toString()

        //3. 결과에 따라 다르게
        var externalResultValue = if(externalFirmbankingResult.resultCode ==0){
            1
        }else{
            2
        }

        //4. 결과를 리턴
        firmBankingPort.updateFirmbankingStatus(
            UpdateFirmbankingStatusRequest(
                FirmbankingRequestId(requestEntity.firmbankingRequestId),
                FirmbankingStatus(externalResultValue),
                UUID.randomUUID().toString()
            )
        )

        //사용자에 알람보내기
    }
}