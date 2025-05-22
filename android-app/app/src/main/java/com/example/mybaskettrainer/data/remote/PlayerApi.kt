package com.example.mybaskettrainer.data.remote

import com.example.mybaskettrainer.data.model.Player
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlayerApi {
    @GET("api/players/{trainerDni}")
    suspend fun getPlayersByTrainer(@Path("trainerDni") trainerDni: String): Response<List<Player>>

    @POST("api/players/{trainerDni}")
    suspend fun createPlayer(@Path("trainerDni") trainerDni: String, @Body player: Player): Response<Player>

    @PUT("api/players/{id}")
    suspend fun updatePlayer(@Path("id") id: Int, @Body player: Player): Response<Player>

    @DELETE("api/players/{id}")
    suspend fun deletePlayer(@Path("id") id: Int): Response<Unit>


}