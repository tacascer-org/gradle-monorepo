package com.github.lowkeylab.ptoscheduler.user.controller

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import com.github.lowkeylab.ptoscheduler.user.resetDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import java.time.LocalDate

@Import(TestcontainersConfiguration::class)
@SpringBootTest
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

            val output =
                webTestClient
                    .post()
                    .bodyValue(createNewUserInputModel)
                    .exchange()

            output
                .expectStatus()
                .isOk
                .expectBody(User::class.java)
                .value {
                    it.name shouldBe name
                    it.maxPtoDays shouldBe maxPtoDays
                }
        }

        test("can find user by id") {
            val user = User("John Doe", 20)
            val savedUser = userRepository.save(user)

            val output =
                webTestClient
                    .get()
                    .uri("/${savedUser.id}")
                    .exchange()

            output
                .expectStatus()
                .isOk
                .expectBody(User::class.java)
                .value {
                    it.shouldBeEqualUsingFields(savedUser)
                }
        }

        test("can randomize a user's PTO days after a certain date") {
            val user = User("John Doe", 20)
            val savedUser = userRepository.save(user)
            val afterDate = LocalDate.of(2022, 1, 1)
            val randomizePtoDaysInputModel = RandomizePtoDaysInputModel(afterDate)

            val output =
                webTestClient
                    .post()
                    .uri("/${savedUser.id}/ptoDays/randomizations")
                    .bodyValue(randomizePtoDaysInputModel)
                    .exchange()

            output.expectStatus().isOk
        }
    })
