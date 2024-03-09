package com.example.githubapiprojectforunittesting.data.repository

import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val api: GithubApi, private val db: UserRepositoriesDatabase) : GithubRepository {
    override suspend fun getCacheRepositories(username: String) = db.getRepositoryDao().getUserRepositories()

    override suspend fun insertUserRepository(userRepository: UserRepository) = db.getRepositoryDao().insertUserRepository(userRepository)

    override fun getUserRepositories(username: String): List<UserRepositoryDto> = api.getUserRepositories(username)

}