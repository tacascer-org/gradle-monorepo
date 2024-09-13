package com.github.lowkeylab.ptoscheduler.user.db

import com.github.lowkeylab.ptoscheduler.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
