package com.example.fakerslist.model


data class ApiResponse(val data: List<PersonData>)

data class PersonData(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val image: String
)
