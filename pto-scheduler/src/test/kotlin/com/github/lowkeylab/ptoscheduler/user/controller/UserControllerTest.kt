package com.github.lowkeylab.ptoscheduler.user.controller

import com.github.lowkeylab.ptoscheduler.TestcontainersConfiguration
import com.github.lowkeylab.ptoscheduler.user.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.client.MockMvcWebTestClient

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class UserControllerTest(
    sut: UserController,
) : FunSpec({
        val webTestClient =
            MockMvcWebTestClient
                .bindToController(sut)
                .configureClient()
                .baseUrl("/users")
                .build()

        test("can create new user") {
            val name = "John Doe"
            val maxPtoDays = 20
            val createNewUserModel = CreateNewUserModel(name, maxPtoDays)

            val output =
                webTestClient
                    .post()
                    .bodyValue(createNewUserModel)
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody(User::class.java)

            output.value {
                it.name shouldBe name
                it.maxPtoDays shouldBe maxPtoDays
            }
        }

        test("can find user by id") {
            val createNewUserModel = CreateNewUserModel("John Doe", 20)
            val user =
                webTestClient
                    .post()
                    .bodyValue(createNewUserModel)
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody(User::class.java)
                    .returnResult()
                    .responseBody!!

            val output =
                webTestClient
                    .get()
                    .uri("/${user.id}")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody(User::class.java)

            output.value {
                it.shouldBeEqualUsingFields(user)
            }
        }
    })
