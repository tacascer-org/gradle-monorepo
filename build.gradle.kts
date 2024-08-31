plugins {
    alias(libs.plugins.sonarqube)
}

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_gradle-monorepo")
        property("sonar.organization", "tacascer-org")
    }
}
