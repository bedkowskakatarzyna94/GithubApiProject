package com.example.githubapiprojectforunittesting.domain.useCases

import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserRepositoriesUseCase @Inject constructor(private val repository: GithubRepository) {
    operator fun invoke(username : String) : Flow<Resource<List<UserRepositoryDto>>> = flow {
        repository.getUserRepositories(username)
    }
}