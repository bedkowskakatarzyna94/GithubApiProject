package com.example.githubapiprojectforunittesting.presentation.usersReposScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.githubapiprojectforunittesting.base.BaseViewModel
import com.example.githubapiprojectforunittesting.common.Constants
import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.model.toUserRepository
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserRepositoriesViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel() {

    private val _userRepositoriesState = mutableStateOf(UserRepositoriesListState())
    val userRepositoriesListState: State<UserRepositoriesListState> = _userRepositoriesState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = Constants.EMPTY)
    val searchTextState: State<String> = _searchTextState

    fun getUserRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = githubRepository.getUserRepositories(searchTextState.value)

            withContext(Dispatchers.Main) {
                _userRepositoriesState.value = when(result) {
                    is Resource.Success -> {
                        UserRepositoriesListState(userRepositories = result.successData.map {it.toUserRepository()})
                    }
                    is Resource.Loading -> {
                        UserRepositoriesListState(isLoading = true)
                    }
                    is Resource.Error -> {
                        UserRepositoriesListState(error = result.errorMessage)
                    }
                }
            }
        }
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}
