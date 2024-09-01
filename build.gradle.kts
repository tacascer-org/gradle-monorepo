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
val globalBuildGroup = "monorepo build"

val lint by tasks.registering {
    group = globalBuildGroup
    dependsOn(tasks.spotlessApply)
    dependsOn(tasks.versionCatalogFormat)
}

tasks.named<TaskReportTask>("tasks") {
    displayGroups = listOf(globalBuildGroup)
}

tasks.check {
    dependsOn(lint)
}
