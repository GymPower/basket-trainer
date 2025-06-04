package com.example.mybaskettrainer.api

import com.example.mybaskettrainer.data.remote.dto.LoginRequest
import com.example.mybaskettrainer.data.remote.dto.LoginResponse
import com.example.mybaskettrainer.data.remote.dto.TrainerRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerTrainer(@Body request: TrainerRequest): Response<LoginResponse>
}