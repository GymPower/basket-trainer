package com.example.mybaskettrainer.data.remote.dto

data class PlayerRequest (
    val playerId: Long? = null,
    val name: String,
    val surname1: String,
    val surname2: String? = null,
    val birthdate: String? = null,
    val email: String? = null,
    val telephone: String? = null,
    val category: String? = null,
    val teamId: Long? = null,
    val trainerDni: String? = null
)