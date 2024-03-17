package com.example.githubapiprojectforunittesting

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.data.repository.GithubRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GithubRepositoryImplTest {

    private lateinit var githubApi: GithubApi
    private lateinit var db: UserRepositoriesDatabase
    private lateinit var repository: GithubRepositoryImpl

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserRepositoriesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        githubApi = mockk()
        repository = GithubRepositoryImpl(githubApi, db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testGetUserRepositoriesWithValidData() = runBlocking {
        // Given
        val username = "example_user"
        val repositories = listOf(
            UserRepositoryDto("repo1", "Description 1"),
            UserRepositoryDto("repo2", "Description 2")
        )
        coEvery { githubApi.getUserRepositories(username) } returns repositories

        // When
        val result = repository.getUserRepositories(username)

        // Then
        Assert.assertTrue(result is Resource.Success)
        Assert.assertEquals(repositories, (result as Resource.Success).data)

        coVerify { githubApi.getUserRepositories(username) }
        confirmVerified(githubApi)
    }

    @Test
    fun testGetUserRepositoriesWithNetworkError() = runBlocking {
        // Given
        val username = "example_user"
        val errorMessage = "Network error"
        coEvery { githubApi.getUserRepositories(username) } throws IOException(errorMessage)

        // When
        val result = repository.getUserRepositories(username)

        // Then
        Assert.assertTrue(result is Resource.Error)
        Assert.assertEquals("Network error: $errorMessage", (result as Resource.Error).message)

        coVerify { githubApi.getUserRepositories(username) }
        confirmVerified(githubApi)
    }

    @Test
    fun testGetUserRepositoriesWithHTTPError() = runBlocking {
        // Given
        val username = "example_user"
        val errorMessage = "HTTP error"
        coEvery { githubApi.getUserRepositories(username) } throws HttpException(Response.error<Any>(400, ResponseBody.create(null, errorMessage)))

        // When
        val result = repository.getUserRepositories(username)

        // Then
        Assert.assertTrue(result is Resource.Error)
        Assert.assertEquals("HTTP error: $errorMessage", (result as Resource.Error).message)

        coVerify { githubApi.getUserRepositories(username) }
        confirmVerified(githubApi)
    }

    @Test
    fun testGetUserRepositoriesWithGeneralError() = runBlocking {
        // Given
        val username = "example_user"
        val errorMessage = "General error"
        coEvery { githubApi.getUserRepositories(username) } throws Exception(errorMessage)

        // When
        val result = repository.getUserRepositories(username)

        // Then
        Assert.assertTrue(result is Resource.Error)
        Assert.assertEquals("Error: $errorMessage", (result as Resource.Error).message)

        coVerify { githubApi.getUserRepositories(username) }
        confirmVerified(githubApi)
    }
}