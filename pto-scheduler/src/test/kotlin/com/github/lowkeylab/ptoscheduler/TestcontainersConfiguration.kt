package com.github.lowkeylab.ptoscheduler

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer

@TestConfiguration
class TestcontainersConfiguration {
    @Bean
    @ServiceConnection
    fun postgresContainer() = PostgreSQLContainer("postgres:16")
}
