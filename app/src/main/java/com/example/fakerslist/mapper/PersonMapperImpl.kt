package com.example.fakerslist.mapper

import com.example.fakerslist.model.Person
import com.example.fakerslist.model.PersonData

class PersonMapperImpl : PersonMapper {
    override fun mapToDomain(apiPerson: PersonData): Person {
        return Person(
            name="${apiPerson.firstname}${apiPerson.lastname}",
            email = apiPerson.email,
            image = apiPerson.image
        )
    }
}