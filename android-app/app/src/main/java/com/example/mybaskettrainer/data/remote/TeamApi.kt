package com.example.mybaskettrainer.data.remote

import com.example.mybaskettrainer.data.model.Team
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TeamApi {
    @GET("api/teams/{trainerDni}")
    suspend fun getTeamsByTrainer(@Path("trainerDni") trainerDni: String): Response<List<Team>>

    @POST("api/teams/{trainerDni}")
    suspend fun createTeam(@Path("trainerDni") trainerDni: String, @Body team: Team): Response<Team>

    @PUT("api/teams/{id}")
    suspend fun updateTeam(@Path("id") id: Long, @Body team: Team): Response<Team>

    @DELETE("api/teams/{id}")
    suspend fun deleteTeam(@Path("id") id: Long): Response<Unit>
}