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

val ciBuildGroup = "Build Tasks (CI)"

tasks.named<TaskReportTask>("tasks") {
    displayGroups = listOf(ciBuildGroup)
}

tasks.lintAll {
    dependsOn(tasks.spotlessApply)
    dependsOn(tasks.versionCatalogFormat)
}
