package com.github.lowkeylab.ptoscheduler.user

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import io.kotest.core.annotation.DoNotParallelize
import io.kotest.core.test.TestCase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate

fun resetDatabase(jdbcTemplate: JdbcTemplate): suspend (TestCase) -> Unit =
    {
        jdbcTemplate.execute("TRUNCATE TABLE user_pto_days, users")
    }

fun createUser(
    name: String,
    maxPtoDays: Int,
    userRepository: UserRepository,
): User =
    userRepository.save(
        User(
            name = name,
            maxPtoDays = maxPtoDays,
        ),
    )

@DoNotParallelize
@Import(TestcontainersConfiguration::class)
@SpringBootTest
annotation class IntegrationTest
