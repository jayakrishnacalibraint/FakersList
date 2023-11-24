package com.example.fakerslist.repository

import android.content.Context
import com.example.fakerslist.ResponseState
import com.example.fakerslist.api.ApiService
import com.example.fakerslist.mapper.PersonMapperImpl
import com.example.fakerslist.model.Person
import com.example.fakerslist.model.PersonData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) :
    PersonRepository {
    override suspend fun getAllPersons(quantity: Int): ResponseState<List<Person>> {
        val maxItems = 1000
        val subsequentFetch = 20
        val allPersons = mutableListOf<Person>()
        val allPersonsData = mutableListOf<PersonData>()

        try {
            var itemsFetched = 0
            var newQuantity = quantity
            val uniqueIds = HashSet<Int>()
            while (itemsFetched < maxItems) {
                val response = apiService.getPerson(newQuantity)
                val personDataList = response.data
                val fetchedItems = personDataList.size
                if (fetchedItems == 0) {
                    break
                }
                allPersonsData.addAll(personDataList)
                for (personData in personDataList) {

                    val person = PersonMapperImpl().mapToDomain(personData)
                    if (!uniqueIds.contains(personData.id)) {
                        uniqueIds.add(personData.id)
                        allPersons.add(person)
                    }

                }
                itemsFetched += fetchedItems

                if (itemsFetched + subsequentFetch <= maxItems) {
                    newQuantity += subsequentFetch
                } else {
                    newQuantity = maxItems - itemsFetched + fetchedItems
                }

            }
            return ResponseState.Success(allPersons)
        } catch (e: Exception) {
            return ResponseState.Error(e)
        }
    }


}