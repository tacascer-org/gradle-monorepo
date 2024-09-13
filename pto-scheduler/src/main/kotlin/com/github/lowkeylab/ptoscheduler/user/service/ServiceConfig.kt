package com.github.lowkeylab.ptoscheduler.user.service

import com.github.lowkeylab.ptoscheduler.user.db.DbConfig
import com.github.lowkeylab.ptoscheduler.user.db.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DbConfig::class)
class ServiceConfig {
    @Bean
    fun userService(userRepository: UserRepository): UserService = UserService(userRepository)
}
