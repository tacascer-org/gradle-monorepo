package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

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
    fun createNew(
        name: String,
        maxPtoDays: Int,
    ): User

    /**
     * Randomize the PTO days of user with [id] after [after].
     */
    fun randomizePtoDays(
        id: Long,
        after: LocalDate,
    ): User
}

@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun findUserById(id: Long) = userRepository.findById(id)

    @Transactional
    override fun createNew(
        name: String,
        maxPtoDays: Int,
    ) = userRepository.save(User(name, maxPtoDays))

    @Transactional
    override fun randomizePtoDays(
        id: Long,
        after: LocalDate,
    ): User =
        userRepository.findById(id)?.let {
            it.randomlyUseRemainingPtoDaysAfter(after)
            it
        } ?: throw IllegalArgumentException("User not found")
}
