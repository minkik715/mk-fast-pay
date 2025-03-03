package io.github.minkik715.mkpay.money.query.adapter.web.out

import io.github.minkik715.mkpay.money.query.adapter.axon.QueryMoneySumByAddress
import io.github.minkik715.mkpay.money.query.application.port.out.svc.InsertMoneyChangeEventByAddress
import io.github.minkik715.mkpay.money.query.domain.MoneySum
import io.github.minkik715.mkpay.money.query.domain.MoneySumByRegion
import io.github.minkik715.mkpay.money.query.domain.MoneySumByRegionId
import io.github.minkik715.mkpay.money.query.domain.RegionName
import org.axonframework.queryhandling.QueryHandler
import org.springframework.format.datetime.DateFormatter
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class DynamoDBAdapter: InsertMoneyChangeEventByAddress {
    private val dynamoDbClient: DynamoDbClient

    init {
        val credentials: AwsBasicCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)
        this.dynamoDbClient = DynamoDbClient.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }


    private fun putItem(pk: String, sk: String) {
        try {
            val attrMap: HashMap<String, AttributeValue> = HashMap<String, AttributeValue>()
            attrMap["PK"] = AttributeValue.builder().s(pk).build()
            attrMap["SK"] = AttributeValue.builder().n(sk).build()

            val request: PutItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(attrMap)
                .build()

            dynamoDbClient.putItem(request)
        } catch (e: DynamoDbException) {
            System.err.println("Error adding an item to the table: $e")
        }
    }

    private fun getItem(id: String) {
        try {
            val attrMap: HashMap<String, AttributeValue> = HashMap<String, AttributeValue>()
            attrMap["PK"] = AttributeValue.builder().s(id).build()

            val request: GetItemRequest = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(attrMap)
                .build()

            val response: GetItemResponse = dynamoDbClient.getItem(request)
            response.item().forEach { key, value -> println("$key: $value") }
        } catch (e: DynamoDbException) {
            System.err.println("Error getting an item from the table: $e")
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
/*        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val formattedDate = dateFormat.format(Date())
        val pk = "$addressName#$dateFormat"
        val sk = moneyIncrease*/
        // 지역 정보 잔액 증가시키기

        // 지역 정보 시간별 잔액 증가시키기

        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val formattedDate = dateFormat.format(Date())
        val pk = addressName
        val sk = moneyIncrease

        putItem(pk, sk.toString())
    }
}