package com.example.githubapiprojectforunittesting.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubapiprojectforunittesting.domain.model.UserRepository

@Database(
    version = 1,
    entities = [UserRepository::class]
)

abstract class UserRepositoriesDatabase : RoomDatabase() {
    abstract fun getRepositoryDao(): UserRepositoryDao

    companion object {
        @Volatile
        private var instance: UserRepositoriesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, UserRepositoriesDatabase::class.java, "repositories_db.db").build()
    }
}