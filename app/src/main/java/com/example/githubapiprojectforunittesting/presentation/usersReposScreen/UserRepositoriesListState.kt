package com.example.githubapiprojectforunittesting.presentation.usersReposScreen

import com.example.githubapiprojectforunittesting.common.Constants
import com.example.githubapiprojectforunittesting.domain.model.UserRepository

data class UserRepositoriesListState(
    val isLoading: Boolean = false,
    val userRepositories: List<UserRepository> = emptyList(),
    val error: String = Constants.EMPTY
)