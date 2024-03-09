package com.example.githubapiprojectforunittesting.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userRepositories")
data class UserRepository(
    @PrimaryKey(autoGenerate = false)
    var name: String,
    var description: String?
)
