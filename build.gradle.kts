plugins {
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.diffplug.spotless)
}

group = "io.github.tacascer"

dependencies {
    kover(projects.predix)
}

spotless {
    kotlin {
        target("*.kts")
        ktlint()
    }
}

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}
