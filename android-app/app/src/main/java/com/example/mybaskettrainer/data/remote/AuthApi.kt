package com.example.mybaskettrainer.data.remote

import com.example.mybaskettrainer.data.remote.dto.LoginRequest
import com.example.mybaskettrainer.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
