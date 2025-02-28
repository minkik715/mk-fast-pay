plugins {
    kotlin("jvm")
}

group = "io.github.minkik715"
version = "1.0-SNAPSHOT"

val axonVersion = "4.11.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.axonframework:axon-spring-boot-starter:$axonVersion")
    implementation("org.axonframework:axon-configuration:$axonVersion")
}

tasks.test {
    useJUnitPlatform()
}
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}