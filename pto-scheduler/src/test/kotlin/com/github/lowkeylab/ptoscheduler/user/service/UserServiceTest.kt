package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDate

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class UserServiceTest(
    sut: UserService,
    userRepository: UserRepository,
    transactionTemplate: TransactionTemplate,
) : FunSpec({
        test("can create new user") {
            val createdUser = sut.createNew("John Doe", 20)

            transactionTemplate.execute {
                val foundUser = userRepository.findById(createdUser.id!!)!!
                foundUser shouldBeEqualUsingFields createdUser
            }
        }

        test("can randomize user's PTO days after a certain date") {
            val user = User("John Doe", 20)
            val savedUser = userRepository.save(user)
            val date = LocalDate.of(2022, 1, 1)

            sut.randomizePtoDays(savedUser.id!!, date)

            transactionTemplate.execute {
                val foundUser = userRepository.findById(savedUser.id!!)!!
                foundUser.ptoDays shouldHaveSize 20
                foundUser.ptoDaysLeft shouldBe 0
            }
        }
    })
