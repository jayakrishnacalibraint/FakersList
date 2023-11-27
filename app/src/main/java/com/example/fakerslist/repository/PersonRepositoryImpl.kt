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
        val maxItems = 1000
        val subsequentFetch = 20
        val allPersons = mutableListOf<PersonData>()

        return try {


            var itemsFetched = 0
            var newQuantity = quantity
            val uniqueIds = HashSet<String>()

            //

            while (itemsFetched < maxItems) {
                val response = apiService.getPerson(newQuantity)
                val personDataList = response.data
                val fetchedItems = personDataList.size
                if (fetchedItems == 0) {
                    break
                }

                for (personData in personDataList) {


                    val personName="${personData.firstname}${personData.lastname} "
                    if (!uniqueIds.contains(personName)) {
                        uniqueIds.add(personName)
                        allPersons.add(personData)
                    }

//                    if (allPersons.size == quantity) {
//                        break;
//                    }

                }
                itemsFetched += fetchedItems

                if (itemsFetched + subsequentFetch <= maxItems) {
                    newQuantity += subsequentFetch
                } else {
                    newQuantity = maxItems - itemsFetched + fetchedItems
                }

            }
            ResponseState.Success(allPersons)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }

    }


}