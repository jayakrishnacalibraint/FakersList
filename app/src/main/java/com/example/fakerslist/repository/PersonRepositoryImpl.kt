package com.example.fakerslist.repository

import com.example.fakerslist.ResponseState
import com.example.fakerslist.api.ApiService
import com.example.fakerslist.model.PersonData
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : PersonRepository {
    override suspend fun getAllPersons(quantity: Int): ResponseState<List<PersonData>> {
        val maxItems = 100
        val allPersons = mutableListOf<PersonData>()

        return try {
            var itemsFetched = 0
            val uniqueIds = HashSet<String>()

            while (itemsFetched <= maxItems) {
                val response = apiService.getPerson(quantity)
                val personDataList = response.data
                val fetchedItems = personDataList.size
                if (fetchedItems == 0) {
                    break
                }

                for (personData in personDataList) {
                    val personName = "${personData.firstname}${personData.lastname} "
                    if (!uniqueIds.contains(personName)) {
                        uniqueIds.add(personName)
                        allPersons.add(personData)
                    }
                }
                itemsFetched += fetchedItems
            }
            ResponseState.Success(allPersons)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }

    }


}