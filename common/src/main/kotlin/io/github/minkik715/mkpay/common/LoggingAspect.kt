package io.github.minkik715.mkpay.common

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect(
    private val loggingProducer: LoggingProducer
){

    @Before("execution(* io.github.minkik715.mkpay.*.adapter.in.web.*.*(..))")
    fun beforeMethodExecution(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name

        loggingProducer.sendMessage("logging", "Before executing method: $methodName")
    }
}