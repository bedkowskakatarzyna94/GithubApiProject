package com.example.githubapiprojectforunittesting.data.remote

import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}/repos")
    fun getUserRepositories(
        @Path("username") username: String
    ): List<UserRepositoryDto>
}