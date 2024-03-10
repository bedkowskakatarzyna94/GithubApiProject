package com.example.githubapiprojectforunittesting.presentation.usersReposScreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.githubapiprojectforunittesting.base.BaseViewModel
import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.model.toUserRepository
import com.example.githubapiprojectforunittesting.domain.useCases.GetUserRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserRepositoriesViewModel @Inject constructor(
    private val userRepositoriesDatabase: UserRepositoriesDatabase,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
) : BaseViewModel() {

    private val _userRepositoriesState = mutableStateOf(UserRepositoriesListState())
    val userRepositoriesListState: State<UserRepositoriesListState> = _userRepositoriesState

    init {
        getUserRepositories("setkrul")
    }

    private fun getUserRepositories(username: String) {
        getUserRepositoriesUseCase.invoke(username).onEach { repository ->
            when (repository) {
                is Resource.Success -> _userRepositoriesState.value =
                    UserRepositoriesListState(userRepositories = repository.successData.map { it.toUserRepository() })

                is Resource.Error -> _userRepositoriesState.value = UserRepositoriesListState(error = repository.toString())
                is Resource.Loading -> _userRepositoriesState.value = UserRepositoriesListState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}
