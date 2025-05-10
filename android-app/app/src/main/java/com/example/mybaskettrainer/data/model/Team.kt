package com.example.mybaskettrainer.data.model
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val teamId: Int,
    val name: String,
    val category: String,
    val league: String?,
    val trainerDni: String?,
    val playerCount: Int = 0,
    val isFavorite: Boolean = false
)