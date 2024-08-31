plugins {
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.sonarqube)
}

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_gradle-monorepo")
        property("sonar.organization", "tacascer-org")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.asFile.get()}/reports/kover/report.xml",
        )
    }
}

dependencies {
    kover(project(":predix"))
}
