package io.github.tacascer.monorepo.project.convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

const val CI_GROUP_NAME = "Build Tasks (CI)"

/**
 * This plugin creates:
 * - lintAll task that runs all lint tasks in all subprojects
 * - checkAll task that runs all check tasks in all subprojects
 * - qualityCheckAll task that runs all qualityCheck tasks in all subprojects
 * - buildAll task that runs all build tasks in all subprojects
 */
class MonorepoConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("lintAll") { task ->
            task.apply {
                group = CI_GROUP_NAME
                description = "Run linters before compiling"
                dependsOn(project.subprojects.map { "${it.name}:lint" })
                dependsOn(project.gradle.includedBuilds.map { it.task(":lintAll") })
            }
        }

        project.tasks.register("lint") { task ->
            task.apply {
                group = CI_GROUP_NAME
                description = "Per-project lint task"
            }
        }

        project.tasks.register("checkAll") { task ->
            task.apply {
                group = CI_GROUP_NAME
                description = "Run all tests"
                dependsOn("lintAll")
                dependsOn(project.subprojects.map { "${it.name}:check" })
                dependsOn(project.gradle.includedBuilds.map { it.task(":checkAll") })
            }
        }

        project.tasks.register("qualityCheckAll") { task ->
            task.apply {
                group = CI_GROUP_NAME
                description = "Run quality checks after tests"
                dependsOn("checkAll")
                dependsOn(project.subprojects.map { "${it.name}:qualityCheck" })
                dependsOn(project.gradle.includedBuilds.map { it.task(":qualityCheckAll") })
            }
        }

        project.tasks.register("qualityCheck") { task ->
            task.apply {
                group = CI_GROUP_NAME
                description = "Per-project quality check task"
            }
        }

        project.tasks.register("buildAll") { task ->
            task.apply {
                group = CI_GROUP_NAME
                description = "Assemble and build all projects"
                dependsOn("qualityCheckAll")
                dependsOn(project.subprojects.map { "${it.name}:build" })
                dependsOn(project.gradle.includedBuilds.map { it.task(":buildAll") })
            }
        }
    }
}
