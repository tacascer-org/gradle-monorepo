plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.adarshr.testLogger.plugin)
    implementation(libs.diffplug.spotless.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.allopen.plugin)
    implementation(libs.kotlin.noarg.plugin)
    implementation(libs.kotlinx.kover.plugin)
    implementation(libs.spring.boot.plugin)
}

repositories {
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
