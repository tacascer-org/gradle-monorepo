package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.user.IntegrationTest
import com.github.lowkeylab.ptoscheduler.user.createUser
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import com.github.lowkeylab.ptoscheduler.user.resetDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import org.springframework.data.repository.findByIdOrNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDate

@IntegrationTest
class UserServiceTest(
    sut: UserService,
    userRepository: UserRepository,
    transactionTemplate: TransactionTemplate,
    jdbcTemplate: JdbcTemplate,
) : FunSpec({
        beforeEach(resetDatabase(jdbcTemplate))
        test("can create new user") {
            val createdUser = sut.createNew("John Doe", 20)

            transactionTemplate.execute {
                val foundUser = userRepository.findByIdOrNull(createdUser.id!!)!!
                foundUser shouldBeEqualUsingFields createdUser
            }
        }

        test("can randomize user's PTO days after a certain date") {
            val user = createUser("John Doe", 20, userRepository)
            val date = LocalDate.of(2022, 1, 1)

            sut.randomizePtoDays(user.id!!, date)

            transactionTemplate.execute {
                val foundUser = userRepository.findByIdOrNull(user.id!!)!!
                foundUser.ptoDays shouldHaveSize 20
            }
        }
    })
