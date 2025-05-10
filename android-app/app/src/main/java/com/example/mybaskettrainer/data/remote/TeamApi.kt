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
    @GET("teams")
    suspend fun getTeams(): Response<List<Team>>

    @POST("teams")
    suspend fun createTeam(@Body team: Team): Response<Team>

    @PUT("teams/{id}")
    suspend fun updateTeam(@Path("id") id: Int, @Body team: Team): Response<Team>

    @DELETE("teams/{id}")
    suspend fun deleteTeam(@Path("id") id: Int): Response<Unit>
}