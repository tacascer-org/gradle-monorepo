package com.github.lowkeylab.ptoscheduler.user.controller

import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping
    fun createNew(
        @RequestBody
        user: NewUserDto,
    ): User = userService.createNew(user.name, user.maxPtoDays)

    @GetMapping("/{id}")
    fun findUserById(
        @PathVariable id: Long,
    ): User? = userService.findUserById(id)
}

data class NewUserDto(
    val name: String,
    val maxPtoDays: Int,
)
