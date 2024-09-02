package io.github.tacascer.monorepo.settings

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

const val CI_GROUP_NAME = "Build Tasks (CI)"

const val DEVELOPER_GROUP_NAME = "Build Tasks (Developer)"

/**
 * This plugin adds the following tasks for CI:
 * - lintAll task that runs all lint tasks in all subprojects and included builds
 * - checkAll task that runs all check tasks in all subprojects and included builds
 * - qualityCheckAll task that runs all qualityCheck tasks in all subprojects and included builds
 * - buildAll task that runs all build tasks in all subprojects and included builds
 *
 * It also adds the following tasks for developers:
 * - lint task that runs linters in a project
 * - qualityCheck task that runs quality checks in a project
 */
class MonorepoSettingsPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.gradle.lifecycle.beforeProject { project ->
            project.defineDeveloperTask("lint", "Run linters before compiling")
            project.defineDeveloperTask("qualityCheck", "Run quality checks after tests")

            project.defineCITask(
                CITaskConfiguration(
                    "lintAll",
                    "Run linters before compiling",
                    emptyList(),
                ),
            )

            project.defineCITask(
                CITaskConfiguration(
                    "checkAll",
                    "Run all tests",
                    listOf("lintAll"),
                ),
            )

            project.defineCITask(
                CITaskConfiguration(
                    "qualityCheckAll",
                    "Run quality checks after tests",
                    listOf("checkAll"),
                ),
            )

            project.defineCITask(
                CITaskConfiguration(
                    "buildAll",
                    "Assemble and build all projects",
                    listOf("qualityCheckAll"),
                ),
            )
        }
    }
}

private fun Project.defineDeveloperTask(
    name: String,
    description: String,
) {
    tasks.register(name) { task ->
        task.apply {
            group = DEVELOPER_GROUP_NAME
            this.description = description
        }
    }
}

private fun Project.defineCITask(configuration: CITaskConfiguration) {
    tasks.register(configuration.name) { task ->
        task.apply {
            group = CI_GROUP_NAME
            description = configuration.description
            dependsOn(configuration.dependsOn)
            dependsOn(subprojects.map { "${it.name}:${configuration.developerTaskName}" })
            dependsOn(gradle.includedBuilds.map { it.task(":${configuration.name}") })
        }
    }
}

/**
 * Configuration for a top-level CI task.
 * @property name The name of the task if it's applied to the root project (i.e. a `settings.gradle.kts` file).
 * @property description The description of the task.
 * @property dependsOn The tasks that this task depends on.
 */
data class CITaskConfiguration(
    val name: String,
    val description: String,
    val dependsOn: List<String>,
) {
    /**
     * The name of the task if this task is for a developer.
     */
    val developerTaskName = name.dropLast(3)
}
