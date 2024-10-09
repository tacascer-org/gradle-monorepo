plugins {
    id("kotlin-spring-conventions")
    alias(libs.plugins.sonarqube)
}

group = "com.github.lowkeylab"
version = "1.0.0" // x-release-please-version

dependencies {
    implementation(libs.gavlyukovskiy.datasourceProxy)
    implementation(libs.liquibase.core)
    implementation(libs.mapstruct)
    implementation(libs.spring.boot.starterDataJpa)
    implementation(libs.spring.boot.starterWeb)
    implementation(libs.spring.boot.starterWebflux)
    implementation(libs.springdoc.openApiStarterWebMvcUi)
    kapt(libs.mapstruct.processor)
    runtimeOnly(libs.postgresql)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            dependencies {
                implementation(libs.testcontainers.postgresql)
            }
        }
    }
}

tasks.sonar {
    dependsOn(tasks.koverXmlReport)
}

tasks.check {
    dependsOn(tasks.sonar)
}

tasks.bootBuildImage {
    val image = "tacascer/${project.name}"
    imageName = image
    tags = listOf("$image:${project.version}", "$image:latest")
    if (System.getenv("DOCKER_HUB_TOKEN") != null) {
        publish = true
        docker {
            publishRegistry {
                username = "tacascer"
                password = System.getenv("DOCKER_HUB_TOKEN")
            }
        }
    }
}

tasks.release {
    dependsOn(tasks.bootBuildImage)
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
