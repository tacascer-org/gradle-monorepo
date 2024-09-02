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
        create("monorepoConventionPlugin") {
            id = "io.github.tacascer.monorepo.settings-convention"
            implementationClass = "io.github.tacascer.monorepo.settings.MonorepoSettingsPlugin"
        }
    }
}
