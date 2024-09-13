package com.github.lowkeylab.ptoscheduler.user.controller

import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping
    fun createNew(
        @RequestBody input: CreateNewUserInputModel,
    ): User = userService.createNew(input.name, input.maxPtoDays)

    @GetMapping("/{id}")
    fun findUserById(
        @PathVariable id: Long,
    ): User? = userService.findUserById(id)

    @PostMapping("/{id}/ptoDays/randomizations")
    fun randomizePtoDays(
        @PathVariable id: Long,
        @RequestBody input: RandomizePtoDaysInputModel,
    ): User = userService.randomizePtoDays(id, input.afterDate)
}

data class RandomizePtoDaysInputModel(
    val afterDate: LocalDate,
)

data class CreateNewUserInputModel(
    val name: String,
    val maxPtoDays: Int,
)
