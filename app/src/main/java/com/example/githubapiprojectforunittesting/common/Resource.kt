package com.example.githubapiprojectforunittesting.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    data class Success<T>(val successData: T) : Resource<T>(successData)
    data class Error<T>(val errorMessage: String, val errorData: T? = null) : Resource<T>(errorData, errorMessage)
    data class Loading<T>(val loadingData: T? = null) : Resource<T>(loadingData)
}