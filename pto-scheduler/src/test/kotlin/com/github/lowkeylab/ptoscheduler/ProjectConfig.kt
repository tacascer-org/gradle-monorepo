package com.github.lowkeylab.ptoscheduler

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringAutowireConstructorExtension
import io.kotest.extensions.spring.SpringExtension

class ProjectConfig : AbstractProjectConfig() {
    override val parallelism: Int = 3

    override fun extensions() = listOf(SpringExtension, SpringAutowireConstructorExtension)
}
