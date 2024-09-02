plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.tacascer.monorepo.projectConvention)
}

group = "io.github.tacascer"
version = "0.0.1" // x-release-please-version
description =
    """
    Suite of tools to parse conventional commits and generate changelogs.
    """.trimIndent()

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_gradle-monorepo_conventional-commits")
        property("sonar.organization", "tacascer-org")
    }
}

tasks.qualityCheckAll {
    dependsOn(tasks.sonar)
}
