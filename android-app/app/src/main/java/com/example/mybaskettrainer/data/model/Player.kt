package com.example.mybaskettrainer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val playerId: Int,
    val name: String,
    val firstSurname: String,
    val secondSurname: String? = null,
    val birthdate: String? = null,
    val email: String? = null,
    val telephone: String? = null,
    val category: String? = null,
    val trainerDni: String? = null,
    val team: Team? = null
)


