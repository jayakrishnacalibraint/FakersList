package com.example.fakerslist.repository

import com.example.fakerslist.ResponseState
import com.example.fakerslist.model.PersonData

interface PersonRepository {

    suspend fun getAllPersons(quantity :Int) :ResponseState<List<PersonData>>

}