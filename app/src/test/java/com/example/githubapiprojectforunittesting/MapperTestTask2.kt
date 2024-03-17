package com.example.githubapiprojectforunittesting

import com.example.githubapiprojectforunittesting.data.model.UserRepositoryDto
import com.example.githubapiprojectforunittesting.data.model.toUserRepository
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat

class MapperTestTask2 {
    @Test
    fun testToUserRepository() {
        val userRepositoryDto = UserRepositoryDto("GithubApp", "Github API project for unit testing")

        val userRepository = userRepositoryDto.toUserRepository()

        assertThat(userRepository.name).isEqualTo("GithubApp")
        assertThat(userRepository.description).isEqualTo("Github API project for unit testing")
    }
}