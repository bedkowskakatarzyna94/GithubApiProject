package com.example.githubapiprojectforunittesting.domain.repository

import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getCacheRepositories(username: String) : Flow<List<UserRepository>>
    suspend fun insertUserRepository(userRepository: UserRepository) : Long
    suspend fun getUserRepositories(username: String) : Flow<Resource<List<UserRepositoryDto>>>
}