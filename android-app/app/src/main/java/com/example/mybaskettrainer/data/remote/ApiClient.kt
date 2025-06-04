package com.example.mybaskettrainer.data.remote

import com.example.mybaskettrainer.api.AuthApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val teamApi: TeamApi by lazy {
        retrofit.create(TeamApi::class.java)
    }


    val playerApi: PlayerApi by lazy {
        retrofit.create(PlayerApi::class.java)
    }



}
