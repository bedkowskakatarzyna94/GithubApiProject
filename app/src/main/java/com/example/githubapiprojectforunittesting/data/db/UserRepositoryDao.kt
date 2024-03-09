package com.example.githubapiprojectforunittesting.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapiprojectforunittesting.domain.model.UserRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRepository(repository: UserRepository) : Long

    @Query("SELECT * FROM userRepositories")
    fun getUserRepositories(): Flow<List<UserRepository>>
}
