package io.github.minkik715.mkpay.common

import jakarta.validation.*


open class SelfValidating<T>(
    val validator: Validator = Validation.buildDefaultValidatorFactory().validator)
{
    protected fun validateSelf() {
        val violations: Set<ConstraintViolation<T>> =  this.validator.validate(this as T)
        if(violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }

    }
}