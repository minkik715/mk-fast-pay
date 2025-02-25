package io.github.minkik715.mkpay.banking.adapter.out.axon.command

import io.github.minkik715.mkpay.banking.adapter.out.axon.aggregate.FirmbankingRequestAggregate
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RequestFirmbankingAxonCommand(
    var aggregateIdentifier: String,

    var fromBankName:String,
    var fromBankAccountNumber:String,
    var toBankName:String,
    var toBankAccountNumber:String,
    var moneyAmount: Int,
    var firmbankingStatus: Int
)