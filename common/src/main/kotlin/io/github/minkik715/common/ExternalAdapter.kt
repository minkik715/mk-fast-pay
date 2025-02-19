package io.github.minkik715.common


import org.springframework.stereotype.Component;

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ExternalAdapter {
}