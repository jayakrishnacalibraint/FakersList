package com.example.fakerslist.repository

import com.example.fakerslist.ResponseState
import com.example.fakerslist.api.ApiService
import com.example.fakerslist.mapper.PersonMapperImpl
import com.example.fakerslist.model.ApiResponse
import com.example.fakerslist.model.Person
import com.example.fakerslist.model.PersonData
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(private val apiService : ApiService) :PersonRepository {
    override suspend fun getAllPersons(quantity: Int): ResponseState<ApiResponse> {
        val maxItems=1000
        val initialFetch=10
        val subsequentFetch=20
        val allPersons= mutableListOf<Person>()
        val allPersonsData= mutableListOf<PersonData>()

        try{
            var itemsFetched=0
            var quantity=initialFetch
            while (itemsFetched<maxItems){
                val response=apiService.getPerson(quantity)
                val personDataList=response.data
                val fetchedItems=personDataList.size
                if(fetchedItems==0) break
                allPersonsData.addAll(personDataList)
                for(personData in personDataList){

                    val person=PersonMapperImpl().mapToDomain(personData)
                    allPersons.add(person)

                }
                itemsFetched+=fetchedItems

                if (itemsFetched+subsequentFetch<=maxItems){
                    quantity+=subsequentFetch
                }
                else{
                    quantity=maxItems-itemsFetched+fetchedItems
                }

            }
            return ResponseState.Success(ApiResponse(allPersonsData))
        }
        catch (e :Exception){
            return ResponseState.Error(e)
        }
    }


}

