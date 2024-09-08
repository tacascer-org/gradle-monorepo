plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.adarshr.testLogger.plugin)
    implementation(libs.diffplug.spotless.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

repositories {
    maven("https://plugins.gradle.org/m2/")
}
