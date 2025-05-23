package com.example.mybaskettrainer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int, // Identificador único para cada evento
    val name: String,
    val description: String,
    val address: String,
    val date: String,
    val time: String
)
