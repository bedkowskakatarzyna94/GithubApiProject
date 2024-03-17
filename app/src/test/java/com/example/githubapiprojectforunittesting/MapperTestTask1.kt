package com.example.githubapiprojectforunittesting

import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.model.toUserRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MapperTestTask1 {

    @Test
    fun testToUserRepository() {

        val userRepositoryDto = UserRepositoryDto("GithubApp", "Github API project for unit testing")

        val userRepository = userRepositoryDto.toUserRepository()

        assertEquals("GithubApp", userRepository.name)
        assertEquals("Github API project for unit testing", userRepository.description)
    }
}