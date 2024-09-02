plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
    alias(libs.plugins.gradle.pluginPublish)
    alias(libs.plugins.gradleup.shadow)
}

group = "io.github.tacascer"
version = "0.0.1" // x-release-please-version

gradlePlugin {
    plugins {
        create("projectConventionPlugin") {
            id = "io.github.tacascer.monorepo.project-convention"
            implementationClass = "io.github.tacascer.monorepo.project.convention.plugin.MonorepoConventionPlugin"
        }
    }
}
