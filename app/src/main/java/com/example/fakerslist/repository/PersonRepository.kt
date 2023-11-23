package com.example.fakerslist.repository

import com.example.fakerslist.ResponseState
import com.example.fakerslist.model.ApiResponse

interface PersonRepository {

    suspend fun getAllPersons(quantity :Int) :ResponseState<ApiResponse>

}