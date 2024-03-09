package com.example.githubapiprojectforunittesting.domain.useCases

import com.example.githubapiprojectforunittesting.data.model.toUserRepository
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserRepositoriesUseCase @Inject constructor(private val repository: GithubRepository, private val username: String) {
    operator fun invoke() : List<UserRepository> = repository.getUserRepositories(username).map { it.toUserRepository() }
}