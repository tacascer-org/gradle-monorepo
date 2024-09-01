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

val lintAll by tasks.registering {
    group = ciBuildGroup
    description = "Run linters before compiling"
    dependsOn(tasks.spotlessApply)
    dependsOn(tasks.versionCatalogFormat)
    dependsOn(subprojects.map { "${it.name}:lint" })
    dependsOn(gradle.includedBuilds.map { it.task(":lintAll") })
}

val checkAll by tasks.registering {
    group = ciBuildGroup
    description = "Run all tests"
    dependsOn(lintAll)
    dependsOn(subprojects.map { "${it.name}:check" })
    dependsOn(gradle.includedBuilds.map { it.task(":checkAll") })
}

val qualityCheckAll by tasks.registering {
    group = ciBuildGroup
    description = "Run quality checks after tests"
    dependsOn(checkAll)
    dependsOn(subprojects.map { "${it.name}:qualityCheck" })
    dependsOn(gradle.includedBuilds.map { it.task(":qualityCheckAll") })
}

val buildAll by tasks.registering {
    group = ciBuildGroup
    description = "Assemble and build all projects"
    dependsOn(qualityCheckAll)
    dependsOn(subprojects.map { "${it.name}:build" })
    dependsOn(gradle.includedBuilds.map { it.task(":buildAll") })
}
