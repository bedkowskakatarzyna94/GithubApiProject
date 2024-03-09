package com.example.githubapiprojectforunittesting.data.model

import com.example.githubapiprojectforunittesting.domain.model.UserRepository

data class UserRepositoryDto(
    val name: String,
    val description: String?
)

fun UserRepositoryDto.toUserRepository(): UserRepository {
    return UserRepository(
        name = name,
        description = description
    )
}
