plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "io.github.tacascer"
version = "0.0.1" // x-release-please-version

val lint by tasks.registering { }

val qualityCheck by tasks.registering {}
