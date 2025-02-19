plugins {
    kotlin("jvm") version "2.1.0"
    id("org.springframework.boot") version "3.4.1"
    id("io.freefair.lombok") version "8.4" apply false
}

group = "io.github.minkik715"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}
kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
kotlin {
    jvmToolchain(17)
}
subprojects {


    tasks.withType<Test> {
        useJUnitPlatform()
    }

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.freefair.lombok")

    dependencies {
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

        testImplementation ("org.testcontainers:mysql")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

tasks.getByName<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    enabled = false
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    enabled = false
}

