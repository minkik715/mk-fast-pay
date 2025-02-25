package io.github.minkik715.mkpay.banking.adapter.out.axon.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class UpdateFirmbankingAxonCommand(
    @TargetAggregateIdentifier
    var aggregateIdentifier: String = "",
    var firmbankingRequestId: Long = 0L,
    var firmbankingStatus: Int = -1
)