package com.github.lowkeylab.ptoscheduler.user

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import io.kotest.core.annotation.DoNotParallelize
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate

@DoNotParallelize
@Import(TestcontainersConfiguration::class)
@SpringBootTest
annotation class IntegrationTest

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

object UserExtensions {
    fun User?.shouldExist() = this.shouldNotBeNull()

    fun User.withName(name: String): User =
        this.apply {
            this.name shouldBe name
        }

    fun User.withMaxPtoDays(maxPtoDays: Int): User =
        this.apply {
            this.maxPtoDays shouldBe maxPtoDays
        }

    fun User.withNoPtoDaysLeft(): User =
        this.apply {
            this.ptoDays shouldHaveSize this.maxPtoDays
        }
}
