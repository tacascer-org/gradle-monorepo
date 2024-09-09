package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class UserServiceTest(
    userService: UserService,
) : FunSpec({
        test("can create new user") {
            val createdUser = userService.createNew("John Doe", 20)

            val foundUser = userService.findUserById(createdUser.id!!)
            foundUser!! shouldBeEqualUsingFields createdUser
        }
    })
