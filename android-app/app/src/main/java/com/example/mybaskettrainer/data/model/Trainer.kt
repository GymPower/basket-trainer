package com.example.mybaskettrainer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Trainer(
    val trainerDni: String,
    val username: String,
    val password: String,
    val name: String,
    val firstSurname: String,
    val secondSurname: String? = null,
    val birthdate: String? = null,
    val email: String? = null,
    val telephone: String? = null

)
