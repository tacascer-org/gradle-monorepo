package com.github.lowkeylab.ptoscheduler.user

import io.kotest.core.test.TestCase
import org.springframework.jdbc.core.JdbcTemplate

fun resetDatabase(jdbcTemplate: JdbcTemplate): suspend (TestCase) -> Unit =
    {
        jdbcTemplate.execute("TRUNCATE TABLE user_pto_days, users")
    }
