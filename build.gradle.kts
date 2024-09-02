plugins {
    alias(libs.plugins.benManes.versions)
    alias(libs.plugins.diffplug.spotless)
    alias(libs.plugins.littlerobots.versionCatalogUpdate)
}

group = "io.github.tacascer"

spotless {
    kotlin {
        target("*.kts")
        ktlint()
    }
}

tasks.lintAll {
    dependsOn(tasks.spotlessApply)
    dependsOn(tasks.versionCatalogFormat)
}
