package com.example.githubapiprojectforunittesting.data.repository

import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApi: GithubApi, private val db: UserRepositoriesDatabase) : GithubRepository {

    override suspend fun getCacheRepositories(username: String) = db.getRepositoryDao().getUserRepositories()

    override suspend fun insertUserRepository(userRepository: UserRepository) = db.getRepositoryDao().insertUserRepository(userRepository)

    override suspend fun getUserRepositories(username: String): Resource<List<UserRepositoryDto>> {
        return try {
            val repositories = githubApi.getUserRepositories(username)
            Resource.Success(repositories)
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")
        }
    }
}