package io.github.tacascer.monorepo.settings

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.diagnostics.TaskReportTask

const val CI_GROUP_NAME = "CI"

const val DEVELOPER_GROUP_NAME = "Developer"

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
            project.pluginManager.apply(BasePlugin::class.java)
            project.defineDeveloperTasks()
            project.defineCITasks()
        }
        settings.gradle.lifecycle.afterProject { project ->
            project.tasks.named("build") {
                it.group = DEVELOPER_GROUP_NAME
            }
            project.tasks.named("check") {
                it.group = DEVELOPER_GROUP_NAME
            }
            project.tasks.named("tasks", TaskReportTask::class.java) {
                it.displayGroups = listOf(CI_GROUP_NAME, DEVELOPER_GROUP_NAME)
            }
        }
    }
}

private fun Project.defineDeveloperTasks() {
    registerDeveloperTasks("lint")
    registerDeveloperTasks("qualityCheck")
}

private fun Project.defineCITasks() {
    registerCITasks(
        CITaskConfiguration(
            "lintAll",
            emptyList(),
        ),
    )

    registerCITasks(
        CITaskConfiguration(
            "checkAll",
            listOf("lintAll"),
        ),
    )

    registerCITasks(
        CITaskConfiguration(
            "qualityCheckAll",
            listOf("checkAll"),
        ),
    )

    registerCITasks(
        CITaskConfiguration(
            "buildAll",
            listOf("qualityCheckAll"),
        ),
    )
}

private fun Project.registerDeveloperTasks(name: String) {
    tasks.register(name) { task ->
        task.apply {
            group = DEVELOPER_GROUP_NAME
            this.description = "Run $name in this project"
        }
    }
}

private fun Project.registerCITasks(configuration: CITaskConfiguration) {
    tasks.register(configuration.name) { task ->
        task.apply {
            group = CI_GROUP_NAME
            description = configuration.description
            dependsOn(configuration.dependsOn)
            dependsOn(configuration.developerTaskName)
            dependsOn(subprojects.map { "${it.name}:${configuration.developerTaskName}" })
            dependsOn(gradle.includedBuilds.map { it.task(":${configuration.name}") })
        }
    }
}

/**
 * Configuration for a top-level CI task.
 * @property name The name of the task if it's applied to the root project (i.e. a `settings.gradle.kts` file).
 * @property dependsOn The tasks that this task depends on.
 */
data class CITaskConfiguration(
    val name: String,
    val dependsOn: List<String>,
) {
    /**
     * The name of the task if this task is for a developer.
     */
    val developerTaskName = name.dropLast(3)

    val description = "Run $developerTaskName in this project, all subprojects, and included builds"
}
