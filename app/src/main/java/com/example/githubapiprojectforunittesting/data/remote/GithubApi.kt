package com.example.githubapiprojectforunittesting.data.remote

import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): List<UserRepositoryDto>
}