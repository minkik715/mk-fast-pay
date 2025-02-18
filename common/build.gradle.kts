plugins {
    kotlin("jvm")
}

group = "io.github.minkik715"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
}

tasks.test {
    useJUnitPlatform()
}
