package com.example.mybaskettrainer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrainerRequest(
    @SerializedName("dni") val dni: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname1") val firstSurname: String,
    @SerializedName("surname2") val secondSurname: String? = null,
    @SerializedName("birthdate") val birthdate: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val telephone: String? = null
)