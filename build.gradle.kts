plugins {
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.diffplug.spotless)
}

group = "io.github.tacascer"

spotless {
    kotlin {
        target("*.kts")
        ktlint()
    }
}
