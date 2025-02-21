plugins {
    kotlin("jvm") version "2.1.0"
    id("org.springframework.boot") version "3.4.1"
    id("io.freefair.lombok") version "8.4" apply false
    id("io.spring.dependency-management") version "1.1.4"
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
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.kafka:spring-kafka")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("io.github.openfeign:feign-jackson")



        testImplementation ("org.testcontainers:mysql")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0") // 최신 버전 확인 후 사용
        }
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

