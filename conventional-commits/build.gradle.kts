plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.sonarqube)
}

group = "io.github.tacascer"
version = "0.0.1" // x-release-please-version
description =
    """
    Suite of tools to parse conventional commits and generate changelogs.
    """.trimIndent()

kotlin {
    jvm()
    linuxX64()
}

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_gradle-monorepo_conventional-commits")
        property("sonar.organization", "tacascer-org")
    }
}

tasks.qualityCheck {
    dependsOn(tasks.sonar)
}
