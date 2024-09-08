import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("kotlin-jvm-conventions")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.springframework.boot")
}

configurations {
    compileOnly {
        extendsFrom(configurations.kapt.get())
    }
}

val libs = the<LibrariesForLibs>()

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
    implementation(libs.spring.boot.starterActuator)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            dependencies {
                implementation(libs.kotest.extensions.spring)
                implementation(libs.ninjaSquad.springmockk)
                implementation(libs.spring.boot.starterTest)
                implementation(libs.spring.boot.testcontainers)
            }
        }
    }
}

allOpen {
    annotation("jakarta.transaction.Transactional")
}
