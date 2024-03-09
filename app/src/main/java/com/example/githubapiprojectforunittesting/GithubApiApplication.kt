package com.example.githubapiprojectforunittesting

import android.app.Application

class GithubApiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: GithubApiApplication
    }
}