package com.example.githubapiprojectforunittesting.domain.useCases

import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRepositoriesUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend fun invoke(username : String) : Flow<Resource<List<UserRepositoryDto>>> = repository.getUserRepositories(username)
}