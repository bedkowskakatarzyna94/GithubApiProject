package com.example.githubapiprojectforunittesting.presentation.usersReposScreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.githubapiprojectforunittesting.base.BaseViewModel
import com.example.githubapiprojectforunittesting.data.model.toUserRepository
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRepositoriesViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val githubApi: GithubApi
) : BaseViewModel() {

    private val _userRepositoriesState = mutableStateOf(UserRepositoriesListState())
    val userRepositoriesListState: State<UserRepositoriesListState> = _userRepositoriesState

    init {
        getUserRepositories("setkrul")
    }

    private fun getUserRepositories(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = githubApi.getUserRepositories(username)
            _userRepositoriesState.value =
                UserRepositoriesListState(userRepositories = result.map { it.toUserRepository() })
            //githubRepository.getUserRepositories(username)
        }
    }
}
