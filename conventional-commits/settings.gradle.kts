rootProject.name = "conventional-commits"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    pluginManagement {
        includeBuild("../monorepo-convention-plugins")
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
