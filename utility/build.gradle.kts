plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j")
    implementation("org.json:json:20240303") // 최신 버전 사용
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

}

kotlin {
    jvmToolchain(17)
}