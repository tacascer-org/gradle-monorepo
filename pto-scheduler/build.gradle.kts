plugins {
    id("kotlin-spring-conventions")
    alias(libs.plugins.sonarqube)
}

group = "com.github.lowkeylab"
version = "0.0.1" // x-release-please-version

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}

tasks.qualityCheck {
    dependsOn(tasks.sonar)
}

sonar {
    properties {
        property("sonar.projectKey", "lowkeylab_gradle-monorepo_pto-scheduler")
        property("sonar.organization", "lowkeylab")
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
