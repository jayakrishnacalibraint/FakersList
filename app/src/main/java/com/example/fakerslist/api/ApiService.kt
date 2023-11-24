package com.example.fakerslist.api

import com.example.fakerslist.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("persons")
    suspend fun getPerson(@Query("_quantity") quantity :Int) :ApiResponse


}