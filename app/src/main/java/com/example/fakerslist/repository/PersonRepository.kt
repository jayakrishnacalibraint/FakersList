package com.example.fakerslist.repository

import com.example.fakerslist.ResponseState
import com.example.fakerslist.model.ApiResponse
import com.example.fakerslist.model.Person

interface PersonRepository {

    suspend fun getAllPersons(quantity :Int) :ResponseState<List<Person>>

}