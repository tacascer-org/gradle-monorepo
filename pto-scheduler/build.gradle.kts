plugins {
    id("kotlin-spring-conventions")
    alias(libs.plugins.sonarqube)
}

group = "com.github.lowkeylab"
version = "0.0.1" // x-release-please-version

dependencies {
    implementation(libs.gavlyukovskiy.datasourceProxy)
    implementation(libs.liquibase.core)
    implementation(libs.mapstruct)
    implementation(libs.spring.boot.starterDataJpa)
    implementation(libs.spring.boot.starterWeb)
    implementation(libs.spring.boot.starterWebflux)
    kapt(libs.mapstruct.processor)
    runtimeOnly(libs.postgresql)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            dependencies {
                implementation(libs.testcontainers.postgresql)
            }
        }
    }
}

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}

tasks.check {
    dependsOn(tasks.sonar)
}

sonar {
    properties {
        property("sonar.projectKey", "lowkeylab_gradle-monorepo_pto-scheduler")
        property("sonar.organization", "lowkeylab")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.asFile.get()}/reports/kover/report.xml",
        )
        property(
            "sonar.userHome",
            "${layout.buildDirectory.asFile.get()}/.sonar",
        )
    }
}
