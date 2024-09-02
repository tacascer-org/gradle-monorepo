rootProject.name = "monorepo-convention-plugins"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
    pluginManagement {
        repositories {
            gradlePluginPortal()
        }
    }
}

include("settings-convention-plugin")
