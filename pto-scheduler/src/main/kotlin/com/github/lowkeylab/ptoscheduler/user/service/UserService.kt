package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
interface UserService {
    /**
     * Find a user by their ID.
     * @return The user, or null if not found.
     */
    fun findUserById(id: Long): User?

    /**
     * Create a new user.
     * @return The newly created user.
     */
    @Transactional
    fun createNew(
        name: String,
        maxPtoDays: Int,
    ): User

    /**
     * Randomize the PTO days of user with [id] after [after].
     */
    @Transactional
    fun randomizePtoDays(
        id: Long,
        after: LocalDate,
    ): User
}

class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun findUserById(id: Long) = userRepository.findById(id)

    override fun createNew(
        name: String,
        maxPtoDays: Int,
    ) = userRepository.save(User(name, maxPtoDays))

    override fun randomizePtoDays(
        id: Long,
        after: LocalDate,
    ): User =
        userRepository.findById(id)?.let {
            it.randomizePtoDaysAfter(after)
            userRepository.update(it)
            it
        } ?: throw IllegalArgumentException("User not found")
}
