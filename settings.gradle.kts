plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "mk-fast-pay"
include("membership-service")
include("common")
include("banking-service")
include("money-service")
include("task-consumer")
include("remittance-service")
include("payment-service")
include("payment-service")
include("utility")
include("money-aggregation-service")
