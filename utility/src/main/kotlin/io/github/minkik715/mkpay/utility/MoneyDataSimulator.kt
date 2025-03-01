package io.github.minkik715.mkpay.utility

import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


object DummyMoneyDataSimulator {
    private const val DECREASE_API_ENDPOINT = "http://localhost:8083/money/decrease-eda"
    private const val INCREASE_API_ENDPOINT = "http://localhost:8083/money/increase-eda"

    private const val CREATE_MONEY_API_ENDPOINT = "http://localhost:8083/money/create-member-money"
    private const val REGISTER_ACCOUNT_API_ENDPOINT = "http://localhost:8082/bank-accounts-eda"

    private val BANK_NAME = arrayOf("국민", "신한", "우리")

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val random: Random = Random()
        val readyMemberList: MutableList<Int> = ArrayList()

        while (true) {
            var amount: Int = random.nextInt(20001) - 10000 // Random number between -100000 and 100000
            val targetMembershipId: Int = random.nextInt(10000) + 1 // Random number between 1 and 100000

            registerAccountSimulator(REGISTER_ACCOUNT_API_ENDPOINT, targetMembershipId)
            createMemberMoneySimulator(CREATE_MONEY_API_ENDPOINT, targetMembershipId)
            Thread.sleep(1000)
            readyMemberList.add(targetMembershipId)

            increaseMemberMoneySimulator(INCREASE_API_ENDPOINT, amount, targetMembershipId)

            amount = random.nextInt(20001) - 10000 // Random number between -100000 and 100000
            val decreaseTargetMembershipId = readyMemberList[random.nextInt(readyMemberList.size)]
            increaseMemberMoneySimulator(DECREASE_API_ENDPOINT, amount, decreaseTargetMembershipId)

            try {
                Thread.sleep(1000) // Wait for 1 second before making the next API call
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun increaseMemberMoneySimulator(apiEndpoint: String, amount: Int, targetMembershipId: Int) {
        try {
            val url = URL(apiEndpoint)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setDoOutput(true)

            val jsonRequestBody: JSONObject = JSONObject()
            jsonRequestBody.put("amount", amount)
            jsonRequestBody.put("targetMembershipId", targetMembershipId)

            call(apiEndpoint, conn, jsonRequestBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun registerAccountSimulator(apiEndpoint: String, targetMembershipId: Int) {
        try {
            val url: URL = URL(apiEndpoint)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setDoOutput(true)

            val random: Random = Random()

            val jsonRequestBody: JSONObject = JSONObject()
            jsonRequestBody.put("bankAccountNumber", generateRandomAccountNumber())
            jsonRequestBody.put(
                "bankName",
                BANK_NAME[random.nextInt(BANK_NAME.size)]
            )
            jsonRequestBody.put("membershipId", targetMembershipId)
            jsonRequestBody.put("valid", true)

            call(apiEndpoint, conn, jsonRequestBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createMemberMoneySimulator(apiEndpoint: String, targetMembershipId: Int) {
        try {
            val url: URL = URL(apiEndpoint)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setDoOutput(true)

            val jsonRequestBody: JSONObject = JSONObject()
            jsonRequestBody.put("targetMembershipId", targetMembershipId)

            call(apiEndpoint, conn, jsonRequestBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun call(apiEndpoint: String, conn: HttpURLConnection, jsonRequestBody: JSONObject) {
        val outputStream: OutputStream = conn.getOutputStream()
        outputStream.write(jsonRequestBody.toString().toByteArray())
        outputStream.flush()

        val reader = BufferedReader(InputStreamReader(conn.getInputStream()))
        var line: String?
        val response = StringBuilder()

        while ((reader.readLine().also { line = it }) != null) {
            response.append(line)
        }
        reader.close()

        println("API Response from $apiEndpoint: $response")
    }

    private fun generateRandomAccountNumber(): String {
        val sb = StringBuilder()
        val random: Random = Random()

        for (i in 0..9) {
            val digit: Int = random.nextInt(10) // Generate a random digit (0 to 9)
            sb.append(digit)
        }

        return sb.toString()
    }
}