package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.user.User
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {
    /**
     * Find a user by their ID.
     * @return The user, or null if not found.
     */
    fun findById(id: Long) = userRepository.findByIdOrNull(id)

    /**
     * Create a new user.
     * @return The newly created user.
     */
    @Transactional
    fun createNew(
        name: String,
        maxPtoDays: Int,
    ) = userRepository.save(User(name, maxPtoDays))

    /**
     * Randomize the PTO days of user with [id] after [afterDate].
     */
    @Transactional
    fun randomizePtoDays(
        id: Long,
        afterDate: LocalDate,
    ): User =
        userRepository.findByIdOrNull(id)?.let { user ->
            user.randomizePtoDaysAfter(afterDate)
            user
        } ?: throw IllegalArgumentException("User with ID $id not found")
}
