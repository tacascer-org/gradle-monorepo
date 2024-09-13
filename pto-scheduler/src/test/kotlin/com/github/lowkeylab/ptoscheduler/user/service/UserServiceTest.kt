package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.support.TransactionTemplate

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
    })
