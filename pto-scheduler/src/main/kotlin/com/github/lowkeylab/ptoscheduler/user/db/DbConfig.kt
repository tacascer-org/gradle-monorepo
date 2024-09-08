package com.github.lowkeylab.ptoscheduler.user.db

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories
class DbConfig {
    @Bean
    fun jpaUserRepository(
        userEntityRepository: UserEntityRepository,
        userEntityMapper: UserEntityMapper,
    ): JpaUserRepository = JpaUserRepository(userEntityRepository, userEntityMapper)
}
