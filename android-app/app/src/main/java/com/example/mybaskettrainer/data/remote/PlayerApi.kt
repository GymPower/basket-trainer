package com.example.mybaskettrainer.data.remote

import com.example.mybaskettrainer.data.model.Player
import com.example.mybaskettrainer.data.remote.dto.PlayerRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlayerApi {
    @GET("api/players/trainer/{trainerDni}")
    suspend fun getPlayersByTrainer(@Path("trainerDni") trainerDni: String): Response<List<PlayerRequest>>

    @POST("api/players/{teamId}/{trainerDni}")
    suspend fun createPlayer(@Path("teamId") teamId: Long, @Path("trainerDni") trainerDni: String, @Body player: PlayerRequest): Response<PlayerRequest>

    @PUT("api/players/{id}")
    suspend fun updatePlayer(@Path("id") id: Long, @Body player: PlayerRequest): Response<PlayerRequest>

    @DELETE("api/players/{id}")
    suspend fun deletePlayer(@Path("id") id: Long): Response<Unit>
}