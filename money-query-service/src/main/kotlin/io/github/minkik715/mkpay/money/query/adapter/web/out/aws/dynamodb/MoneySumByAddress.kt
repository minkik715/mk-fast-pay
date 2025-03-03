package io.github.minkik715.mkpay.money.query.adapter.web.out.aws.dynamodb

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

data class MoneySumByAddress(
    val PK: String,
    val SK: String,
    val balance: Long
){
    companion object {
        fun fromDynamoAttr(item : Map<String, AttributeValue>): MoneySumByAddress {
            return MoneySumByAddress(
                item["PK"]!!.s(),
                item["SK"]!!.s(),
                item["balance"]!!.n().toLong()
            )
        }
    }
}