package com.github.lowkeylab.ptoscheduler.user.db

import com.github.lowkeylab.ptoscheduler.user.User
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class UserEntityMapper {
    abstract fun toEntity(user: User): UserEntity

    abstract fun toDto(userEntity: UserEntity): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(
        user: User,
        @MappingTarget userEntity: UserEntity,
    ): UserEntity
}
