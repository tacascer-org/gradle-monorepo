plugins {
    alias(libs.plugins.adarshr.testLogger)
    alias(libs.plugins.diffplug.spotless)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.spring.boot)
    `jvm-test-suite`
}

group = "io.github.tacascer"
version = "1.4.0" // x-release-please-version

dependencies {
    with(platform(libs.spring.boot.dependencies)) {
        compileOnly(this)
        developmentOnly(this)
        implementation(this)
    }
    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.dockerCompose)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    implementation(libs.liquibase.core)
    implementation(libs.mapstruct)
    implementation(libs.spring.boot.starterActuator)
    implementation(libs.spring.boot.starterDataJpa)
    implementation(libs.spring.boot.starterWeb)
    implementation(libs.springdoc.openApiStarterWebMvcUi)
    kapt(libs.mapstruct.processor)
    runtimeOnly(libs.postgresql)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.transaction.Transactional")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
            dependencies {
                implementation(libs.kotest.extensions.spring)
                implementation(libs.kotest.property)
                implementation(libs.kotest.runnerJunit5)
                implementation(libs.ninjaSquad.springmockk)
                implementation(libs.spring.boot.starterTest)
                implementation(libs.spring.boot.testcontainers)
                implementation(libs.testcontainers.junitJupiter)
                implementation(libs.testcontainers.postgresql)
            }
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
    jvmToolchain(21)
}

spotless {
    kotlin {
        ktlint()
    }
}
