package com.example.fakerslist.repository

import com.example.fakerslist.ResponseState
import com.example.fakerslist.api.ApiService
import com.example.fakerslist.model.PersonData
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) :
    PersonRepository {
    override suspend fun getAllPersons(quantity: Int): ResponseState<List<PersonData>> {
        val allPersons = mutableListOf<PersonData>()

        return try {
            val uniqueIds = HashSet<String>()
            val response = apiService.getPerson(quantity)
            val personDataList = response.data
//            for (personData in personDataList) {
//                val personName = "${personData.firstname}${personData.lastname} "
////                if (!uniqueIds.contains(personName)) {
////                    uniqueIds.add(personName)
//                    allPersons.add(personData)
////                }
//
//            }
            ResponseState.Success(personDataList)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }

    }


}