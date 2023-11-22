package com.example.fakerslist.mapper

import com.example.fakerslist.model.Person
import com.example.fakerslist.model.PersonData

interface PersonMapper {

    fun mapToDomain(apiPerson :PersonData) :Person
}