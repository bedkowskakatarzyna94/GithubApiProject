import com.example.githubapiprojectforunittesting.common.Resource
import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.db.UserRepositoryDao
import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.data.repository.GithubRepositoryImpl
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GithubRepositoryImplTest {

    @Mock
    private lateinit var mockGithubApi: GithubApi

    @Mock
    private lateinit var mockDb: UserRepositoriesDatabase

    private lateinit var githubRepository: GithubRepositoryImpl

    @Before
    fun setUp() {
        githubRepository = GithubRepositoryImpl(mockGithubApi, mockDb)
    }

    @Test
    fun `test getCacheRepositories`() {
        runBlocking {
            val fakeRepositories = listOf(UserRepository("repo1", "desc1"), UserRepository("repo2", "desc2"))

            val mockUserRepositoryDao = mock(UserRepositoryDao::class.java)
            `when`(mockDb.getRepositoryDao()).thenReturn(mockUserRepositoryDao)
            `when`(mockUserRepositoryDao.getUserRepositories()).thenReturn(flowOf(fakeRepositories))

            val result = githubRepository.getCacheRepositories("username").toList().flatten()

            assertEquals(fakeRepositories, result)
        }
    }

    @Test
    fun `test insertUserRepository`() {
        runBlocking {
            val userRepository = UserRepository("repo1", "desc1")

            val mockUserRepositoryDao = mock(UserRepositoryDao::class.java)
            `when`(mockDb.getRepositoryDao()).thenReturn(mockUserRepositoryDao)
            `when`(mockUserRepositoryDao.insertUserRepository(userRepository)).thenReturn(1L)

            val result = githubRepository.insertUserRepository(userRepository)

            assertEquals(1L, result)
        }
    }

    @Test
    fun `test getUserRepositories success`() {
        runBlocking {
            val username = "username"
            val fakeRepositories = listOf(UserRepositoryDto("repo1", "desc1"), UserRepositoryDto("repo2", "desc2"))
            `when`(mockGithubApi.getUserRepositories(username)).thenReturn(fakeRepositories)

            val result = githubRepository.getUserRepositories(username)

            assertEquals(Resource.Success(fakeRepositories), result)
        }
    }

    @Test
    fun `test getUserRepositories network error`() {
        runBlocking {
            val username = "username"

            `when`(mockGithubApi.getUserRepositories(username)).thenAnswer {
                throw IOException("Network error")
            }

            val result = githubRepository.getUserRepositories(username)

            assertEquals(Resource.Error("Network error: Network error", null), result)
        }
    }


    @Test
    fun `test getUserRepositories http error`() {
        runBlocking {
            val username = "username"

            val mockResponse: Response<List<UserRepository>> = mock()
            `when`(mockResponse.code()).thenReturn(404) // Customize the HTTP status code as needed

            `when`(mockGithubApi.getUserRepositories(username)).thenAnswer{
                throw HttpException(mockResponse)
            }

            val result = githubRepository.getUserRepositories(username)

            assertEquals(Resource.Error("HTTP error: HTTP 404 null", null), result)
        }
    }

    @Test
    fun `test getUserRepositories other error`() {
        runBlocking {
            val username = "username"

            `when`(mockGithubApi.getUserRepositories(username)).thenThrow(RuntimeException())

            val result = githubRepository.getUserRepositories(username)

            assertEquals(Resource.Error("Error: null", null), result)
        }
    }

}
