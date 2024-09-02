plugins {
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kotlinx.kover)
}

version = "0.0.1" // x-release-please-version

// Duplicating functionality of project convention plugin because it is not available in the monorepo-convention-plugins project.
val lintAll by tasks.registering { }

val checkAll by tasks.registering { }

val qualityCheckAll by tasks.registering { }

val buildAll by tasks.registering { }

dependencies {
    kover(project(":settings-convention-plugin"))
}

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_gradle-monorepo_convention-plugins")
        property("sonar.organization", "tacascer-org")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.asFile.get()}/reports/kover/report.xml",
        )
    }
}

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}

qualityCheckAll {
    dependsOn(tasks.sonar)
}
