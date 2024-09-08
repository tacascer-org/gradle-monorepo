package com.github.lowkeylab.ptoscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PtoSchedulerApplication

fun main(args: Array<String>) {
    runApplication<PtoSchedulerApplication>(*args)
}
