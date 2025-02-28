package io.github.minkik715.mkpay.banking.application.service

import io.github.minkik715.mkpay.banking.adapter.out.axon.command.CreateRegisteredBankAccountAxonCommand
import io.github.minkik715.mkpay.banking.adapter.out.axon.event.CreateRegisteredBankAccountAxonEvent
import io.github.minkik715.mkpay.common.UseCase
import io.github.minkik715.mkpay.banking.application.port.`in`.FindBankAccountsCommand
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.GetBankAccountRequest
import io.github.minkik715.mkpay.banking.application.port.`in`.BankAccountUseCase
import io.github.minkik715.mkpay.banking.application.port.`in`.RegisterAccountCommand
import io.github.minkik715.mkpay.banking.application.port.out.persistence.bankaccount.BankAccountPort
import io.github.minkik715.mkpay.banking.application.port.out.external.BankExternalPort
import io.github.minkik715.mkpay.banking.application.port.out.svc.membership.MembershipPort
import io.github.minkik715.mkpay.banking.domain.*
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import java.util.*

@UseCase
class BankAccountService(
    private val bankAccountPort: BankAccountPort,
    private val bankExternalPort: BankExternalPort,
    private val membershipPort: MembershipPort,
    private val commandGateway: CommandGateway,
): BankAccountUseCase {
    override fun createRegisteredBankAccount(command: RegisterAccountCommand): BankAccount? {

        membershipPort.getMembership(command.membershipId)?.let {
            if(!it.isValid) return null
        }?: return null


        // 은행 계좌를 등록해야하는 서비스 ( 비즈니스 로직)

        // (멤버 서비스 확인) 잠시 SKIP

        //1. 외부 실제 은행에 등록된 계좌인지 확인한다.
        // 외부의 은행에 이 계좌 정산인지? 확인을 확인해야함.
        // BIZ LOGIC -> EXternal System Port -> Adapter

        // 2. 등록가능한 계좌라면, 등록한다.

        // 2-1. 등록가능하지 않ㄴ은 계좌라면, 에러를 리런
        val bankAccountInfo = bankExternalPort.getBankAccountInfo(
            GetBankAccountRequest(
                command.bankName,
                command.bankAccountNumber
            )
        )

        if (bankAccountInfo.isValid){
            return bankAccountPort.createRegisteredBankAccount(
                MembershipId(command.membershipId),
                BankName(command.bankName),
                BankAccountNumber(command.bankAccountNumber),
                LinkedStatusIstValid(command.linkedStatusIsValid)
            )
        } else{
            return null
        }
    }

    override fun createRegisteredBankAccountByEvent(command: RegisterAccountCommand) {
        commandGateway.send<String>(CreateRegisteredBankAccountAxonCommand(
            UUID.randomUUID().toString(),
            command.membershipId,
            command.bankName,
            command.bankAccountNumber,
        )).exceptionally {
            throw it
        }
    }

    @EventHandler
    fun on(event: CreateRegisteredBankAccountAxonEvent) {
        bankAccountPort.createRegisteredBankAccount(
            MembershipId(event.membershipId),
            BankName(event.bankName),
            BankAccountNumber(event.bankAccountNumber),
            LinkedStatusIstValid(true),
            BankAccountAggregateIdentifier(event.aggregateIdentifier)
        )
        //사용자에게 알람보내기
    }

    override fun getBankAccounts(command: FindBankAccountsCommand): List<BankAccount> {
        //membershipId 유효성 검사 필요

        return bankAccountPort.findBankAccounts(MembershipId(command.membershipId))
    }


}