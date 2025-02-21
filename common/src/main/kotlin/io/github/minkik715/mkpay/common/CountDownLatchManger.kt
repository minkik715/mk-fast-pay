package io.github.minkik715.mkpay.common

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class CountDownLatchManger {

    private val countDownLatchMap: MutableMap<String, CountDownLatch> = mutableMapOf()
    private val stringMap: MutableMap<String, String> = mutableMapOf()

    fun addCountDownLatch(key: String): CountDownLatch {
        countDownLatchMap[key] = CountDownLatch(1);
        return countDownLatchMap[key]!!
    }

    fun setDataForKey(key: String, data: String){
        stringMap[key] = data
    }

    fun getDataForKey(key: String): String? {
        return stringMap[key]
    }

    fun getCountDownLatch(key: String): CountDownLatch? {
        return countDownLatchMap[key]
    }

    fun clean(key: String){
        stringMap.remove(key)
        countDownLatchMap.remove(key)
    }
}