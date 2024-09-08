plugins {
    id("com.adarshr.test-logger")
    id("com.diffplug.spotless")
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
    jvmToolchain(21)
}

spotless {
    kotlin {
        ktlint()
    }
}

tasks.getByName("lint") {
    dependsOn(tasks.spotlessApply)
}
