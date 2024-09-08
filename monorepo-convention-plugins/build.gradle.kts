plugins {
    base
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kotlinx.kover)
}

version = "0.0.1" // x-release-please-version

// Duplicating functionality of project convention plugin because it is not available in the monorepo-convention-plugins project.
val lint by tasks.registering { }

val qualityCheck by tasks.registering { }

val lintAll by tasks.registering {
    dependsOn(lint)
}

val checkAll by tasks.registering {
    dependsOn(subprojects.map { "${it.name}:check" })
}

val qualityCheckAll by tasks.registering {
    dependsOn(checkAll)
    dependsOn(qualityCheck)
}

val buildAll by tasks.registering { dependsOn(qualityCheckAll) }

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
        property(
            "sonar.userHome",
            "${layout.buildDirectory.asFile.get()}/.sonar",
        )
    }
}

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}

qualityCheck {
    dependsOn(tasks.sonar)
}
