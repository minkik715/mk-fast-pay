package io.github.minkik715.mkpay.utility

import java.sql.Connection
import java.sql.DriverManager
import java.util.*

fun main(){
    val memberDummyDataGenerator = DummyDataGenerator()
    memberDummyDataGenerator.execute()
}

class DummyDataGenerator {
    private val DB_URL = "jdbc:mysql://localhost:3306/mk_pay"

    private val DB_USER = "mysqluser"

    private val DB_PASSWORD = "mysqlpw"

    private val ADDRESSES = arrayOf("강남구", "관악구", "서초구")

    fun execute(){
        kotlin.runCatching {
            val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)

            generateDummy(conn)

            conn.close()

        }.onFailure {
            it.printStackTrace()
        }


    }

    private fun generateDummy(conn: Connection) {
        val insertQuery =
            "INSERT INTO membership (membership_id, address, email, is_corp, is_valid, name) VALUES (?, ?, ?, ?, ?, ?)"

        val random = Random()
        val pstmt = conn.prepareStatement(insertQuery)

        val numberOfDummyData = 1000
        val batchSize = 100  // 한 번에 처리할 배치 크기 설정

        conn.autoCommit = false // 수동 커밋 모드로 설정 (성능 향상)

        for (i in 1..numberOfDummyData) {
            pstmt.setLong(1, i.toLong())
            pstmt.setString(2, ADDRESSES[random.nextInt(ADDRESSES.size)])
            pstmt.setString(3, "email_$i@example.com")
            pstmt.setBoolean(4, random.nextBoolean())
            pstmt.setBoolean(5, random.nextBoolean())
            pstmt.setString(6, "User $i")

            pstmt.addBatch() // 배치 추가

            if (i % batchSize == 0) { // 일정 개수마다 실행
                pstmt.executeBatch()
            }
        }

        pstmt.executeBatch() // 남은 배치 실행
        conn.commit() // 트랜잭션 커밋

        pstmt.close()
        conn.autoCommit = true // 원래 상태로 복구
    }


}