package com.example.githubapiprojectforunittesting.domain.repository

import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getCacheRepositories(username: String) : Flow<List<UserRepository>>
    suspend fun insertUserRepository(userRepository: UserRepository) : Long
    fun getUserRepositories(username: String) : List<UserRepositoryDto>
}