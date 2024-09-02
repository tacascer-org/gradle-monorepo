plugins {
    alias(libs.plugins.sonarqube)
}

version = "0.0.1" // x-release-please-version

// Duplicating functionality of project convention plugin because it is not available in the monorepo-convention-plugins project.
val lintAll by tasks.registering { }

val checkAll by tasks.registering { }

val qualityCheckAll by tasks.registering { }

val buildAll by tasks.registering { }

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_gradle-monorepo_convention-plugins")
        property("sonar.organization", "tacascer-org")
    }
}

qualityCheckAll {
    dependsOn(tasks.sonar)
}
