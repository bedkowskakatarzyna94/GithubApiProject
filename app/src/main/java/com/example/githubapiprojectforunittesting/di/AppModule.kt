package com.example.githubapiprojectforunittesting.di

import android.content.Context
import com.example.githubapiprojectforunittesting.GithubApiApplication
import com.example.githubapiprojectforunittesting.common.Constants
import com.example.githubapiprojectforunittesting.data.db.UserRepositoriesDatabase
import com.example.githubapiprojectforunittesting.data.db.UserRepositoryDao
import com.example.githubapiprojectforunittesting.data.remote.GithubApi
import com.example.githubapiprojectforunittesting.data.repository.GithubRepositoryImpl
import com.example.githubapiprojectforunittesting.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.GITHUB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    fun providesUserRepositoriesDatabase(@ApplicationContext context: Context): UserRepositoriesDatabase {
        return UserRepositoriesDatabase(context)
    }

    @Provides
    fun provideUserRepositoriesDao(userRepositoriesDatabase: UserRepositoriesDatabase): UserRepositoryDao {
        return userRepositoriesDatabase.getRepositoryDao()
    }

    @Provides
    fun provideContext(application: GithubApiApplication): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideGithubRepository(api: GithubApi, db: UserRepositoriesDatabase): GithubRepository {
        return GithubRepositoryImpl(api, db)
    }
}
