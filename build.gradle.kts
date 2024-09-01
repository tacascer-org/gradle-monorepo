plugins {
    alias(libs.plugins.kotlinx.kover)
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
