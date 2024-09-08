package com.github.lowkeylab.ptoscheduler.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class UserTest :
    FunSpec({
        test("User cannot have negative max PTO days") {
            shouldThrow<IllegalArgumentException> {
                User(
                    name = "John Doe",
                    maxPtoDays = -1,
                )
            }
        }

        test("User cannot have zero max PTO days") {
            shouldThrow<IllegalArgumentException> {
                User(
                    name = "John Doe",
                    maxPtoDays = 0,
                )
            }
        }

        test("Initially user has the same amount of PTO days left as max PTO days") {
            val user =
                User(
                    name = "John Doe",
                    maxPtoDays = 20,
                )

            user.ptoDaysLeft shouldBe 20
        }

        test("User can use PTO days") {
            val user =
                User(
                    name = "John Doe",
                    maxPtoDays = 20,
                )

            user.usePtoDay(LocalDate.of(2022, 1, 1))

            user.ptoDaysLeft shouldBe 19
        }

        test("User cannot use PTO day if no PTO days left") {
            val user =
                User(
                    name = "John Doe",
                    maxPtoDays = 1,
                )
            user.usePtoDay(LocalDate.of(2022, 1, 1))

            shouldThrow<IllegalArgumentException> {
                user.usePtoDay(LocalDate.of(2022, 1, 2))
            }
        }

        test("Randomly using remaining PTO days uses all PTO days") {
            val user =
                User(
                    name = "John Doe",
                    maxPtoDays = 20,
                )

            user.randomlyUseRemainingPtoDaysAfter(LocalDate.of(2022, 1, 1))

            user.ptoDaysLeft shouldBe 0
        }

        test("Random PTO days are after the given date") {
            val user =
                User(
                    name = "John Doe",
                    maxPtoDays = 20,
                )
            val date = LocalDate.of(2022, 1, 1)

            user.randomlyUseRemainingPtoDaysAfter(date)

            user.ptoDays.forEach {
                it.shouldBeAfter(date)
            }
        }

        test("Cannot randomly use remaining PTO days if the anchor date is too late in the year") {
            val user =
                User(
                    name = "John Doe",
                    maxPtoDays = 20,
                )
            val date = LocalDate.of(2022, 12, 31)

            shouldThrow<IllegalArgumentException> {
                user.randomlyUseRemainingPtoDaysAfter(date)
            }
        }
    })
