package com.github.lowkeylab.ptoscheduler.user.controller

import com.github.lowkeylab.ptoscheduler.user.IntegrationTest
import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.createUser
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import com.github.lowkeylab.ptoscheduler.user.resetDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import java.time.LocalDate

@IntegrationTest
class UserControllerTest(
    userRepository: UserRepository,
    sut: UserController,
    jdbcTemplate: JdbcTemplate,
) : FunSpec({
        beforeEach(resetDatabase(jdbcTemplate))
        val webTestClient =
            MockMvcWebTestClient
                .bindToController(sut)
                .configureClient()
                .baseUrl("/users")
                .build()

        test("can create new user") {
            val name = "John Doe"
            val maxPtoDays = 20
            val createNewUserInputModel = CreateNewUserInputModel(name, maxPtoDays)

            val result =
                webTestClient
                    .post()
                    .bodyValue(createNewUserInputModel)
                    .exchange()

            result
                .expectStatus()
                .isOk
                .expectBody(User::class.java)
                .value {
                    it.name shouldBe name
                    it.maxPtoDays shouldBe maxPtoDays
                }
        }

        test("can find user by id") {
            val user = createUser("John Doe", 20, userRepository)

            val result =
                webTestClient
                    .get()
                    .uri("/${user.id}")
                    .exchange()

            result
                .expectStatus()
                .isOk
                .expectBody(User::class.java)
                .value {
                    it.shouldBeEqualUsingFields(user)
                }
        }

        test("can randomize a user's PTO days after a certain date") {
            val user = createUser("John Doe", 20, userRepository)
            val afterDate = LocalDate.of(2022, 1, 1)
            val randomizePtoDaysInputModel = RandomizePtoDaysInputModel(afterDate)

            val result =
                webTestClient
                    .post()
                    .uri("/${user.id}/ptoDays/randomizations")
                    .bodyValue(randomizePtoDaysInputModel)
                    .exchange()

            result.expectStatus().isOk
        }
    })
