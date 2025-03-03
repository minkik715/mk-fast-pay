package io.github.minkik715.mkpay.money.query.adapter.web.out.aws.dynamodb

import io.github.minkik715.mkpay.money.query.adapter.axon.QueryMoneySumByAddress
import io.github.minkik715.mkpay.money.query.application.port.out.svc.InsertMoneyChangeEventByAddress
import io.github.minkik715.mkpay.money.query.domain.MoneySum
import io.github.minkik715.mkpay.money.query.domain.MoneySumByRegion
import io.github.minkik715.mkpay.money.query.domain.MoneySumByRegionId
import io.github.minkik715.mkpay.money.query.domain.RegionName
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.text.SimpleDateFormat
import java.util.*

@Component
class DynamoDBAdapter: InsertMoneyChangeEventByAddress {
    private lateinit var dynamoDbClient: DynamoDbClient

    init {
        val credentials: AwsBasicCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)
        this.dynamoDbClient = DynamoDbClient.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }


    private fun putItem(pk: String, sk: String, balance: String) {
        try {
            val attrMap: HashMap<String, AttributeValue> = HashMap<String, AttributeValue>()
            attrMap["PK"] = AttributeValue.builder().s(pk).build()
            attrMap["SK"] = AttributeValue.builder().s(sk).build()
            attrMap["balance"] = AttributeValue.builder().n(balance).build()

            val request: PutItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(attrMap)
                .build()

            dynamoDbClient.putItem(request)
        } catch (e: DynamoDbException) {
            System.err.println("Error adding an item to the table: $e")
        }
    }

    private fun getItem(pk: String, sk: String): MoneySumByAddress? {
        try {
            val attrMap: HashMap<String, AttributeValue> = HashMap<String, AttributeValue>()
            attrMap["PK"] = AttributeValue.builder().s(pk).build()
            attrMap["SK"] = AttributeValue.builder().s(sk).build()

            val request: GetItemRequest = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(attrMap)
                .build()

            val response: GetItemResponse = dynamoDbClient.getItem(request)
            if(!response.hasItem()){
                return null
            }
            return MoneySumByAddress.fromDynamoAttr(response.item())
        } catch (e: DynamoDbException) {
            throw e
        }
    }

    private fun queryItem(id: String) {
        try {
            // PK 만 써도 돼요.
            val attrMap: HashMap<String, Condition> = HashMap<String, Condition>()
            attrMap["PK"] = Condition.builder()
                .attributeValueList(AttributeValue.builder().s(id).build())
                .comparisonOperator(ComparisonOperator.EQ)
                .build()

            val request: QueryRequest = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditions(attrMap)
                .build()

            val response: QueryResponse = dynamoDbClient.query(request)
            response.items().forEach { value -> System.out.println(value) }
        } catch (e: DynamoDbException) {
            System.err.println("Error getting an item from the table: $e")
        }
    }


    @QueryHandler
    fun query(query: QueryMoneySumByAddress): MoneySumByRegion {
        return MoneySumByRegion.generateMoneySumByRegion(
            MoneySumByRegionId(UUID.randomUUID().toString()),
            RegionName(query.address),
            MoneySum(1000)
        )
    }

    companion object {
        private const val TABLE_NAME = "MoneyIncreaseEventByRegion"
        private const val ACCESS_KEY = ""
        private const val SECRET_KEY = ""
    }

    override fun insertMoneyIncreaseEventByAddress(addressName: String, moneyIncrease: Int) {
        //raw event insert
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val formattedDate = dateFormat.format(Date())

        //1. raw event insert 지역 정보 잔액 증가시키기
        // PK: 강남구#250303 SK: 5.000
        val pk = "$addressName#$dateFormat"
        val sk = moneyIncrease.toString()
        putItem(pk, sk, moneyIncrease.toString())


        //2. 지역 정보 시간별 잔액 증가시키기
        // PK: 강남구#250303#summary SK: -1 balance +5000
        val summaryPk = "$pk#summary"
        val summarySK = "=1"
         getItem(summaryPk, summarySK)?.let {
             val balance = it.balance + moneyIncrease
             updateItem(summaryPk, summarySK, balance.toString())
        }?: run{
            putItem(summaryPk, summarySK, moneyIncrease.toString())
        }

        //3. 지역별 정보
        // PK: 강남구 SK: -1 balance: +5000
        val addressPk = addressName
        val addressSk = "-1"
        getItem(addressPk, addressSk)?.let {
            val balance = it.balance + moneyIncrease
            updateItem(addressPk, addressSk, balance.toString())
        }?: run{
            putItem(addressPk, addressSk, moneyIncrease.toString())
        }
    }

    private fun updateItem(pk: String, sk: String, balance: String) {
        try {
            val attrMap = HashMap<String, AttributeValue>()
            attrMap["PK"] = AttributeValue.builder().s(pk).build()
            attrMap["SK"] = AttributeValue.builder().s(sk).build()

            // Create an UpdateItemRequest
            val updateItemRequest = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(attrMap)
                .attributeUpdates(
                    object : HashMap<String?, AttributeValueUpdate?>() {
                        init {
                            put(
                                "balance", AttributeValueUpdate.builder()
                                    .value(AttributeValue.builder().n(balance).build())
                                    .action(AttributeAction.PUT)
                                    .build()
                            )
                        }
                    }
                ).build()

            val response = dynamoDbClient.updateItem(updateItemRequest)

            // 결과 출력.
            val attributes = response.attributes()
            if (attributes != null) {
                for ((attributeName, attributeValue) in attributes) {
                    println("$attributeName: $attributeValue")
                }
            } else {
                println("Item was updated, but no attributes were returned.")
            }
        } catch (e: DynamoDbException) {
            System.err.println("Error getting an item from the table: " + e.message)
        }
    }
}