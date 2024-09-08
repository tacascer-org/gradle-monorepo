package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun findUserById(id: Long): User?

    fun createNew(
        name: String,
        maxPtoDays: Int,
    ): User
}

@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun findUserById(id: Long) = userRepository.findUserById(id)

    @Transactional
    override fun createNew(
        name: String,
        maxPtoDays: Int,
    ) = userRepository.save(User(name, maxPtoDays))
}
