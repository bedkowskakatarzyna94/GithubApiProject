package com.example.githubapiprojectforunittesting.data.repository

import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val api: GithubApi, private val db: UserRepositoriesDatabase) : GithubRepository {

    override suspend fun getCacheRepositories(username: String) = db.getRepositoryDao().getUserRepositories()

    override suspend fun insertUserRepository(userRepository: UserRepository) = db.getRepositoryDao().insertUserRepository(userRepository)

    override suspend fun getUserRepositories(username: String): Flow<Resource<List<UserRepositoryDto>>> = flow {
        try {
            val repositories = api.getUserRepositories(username)
            emit(Resource.Success(successData = repositories))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}