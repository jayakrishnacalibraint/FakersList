package com.example.fakerslist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonData(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val birthday: String,
    val gender: String,
    val address: Address,
    val website: String,
    val image: String,
) : Parcelable

@Parcelize
data class Address(
    val id: Int,
    val street: String,
    val streetName: String,
    val buildingNumber: String,
    val city: String,
    val zipcode: String,
    val country: String,
    val county_code: String,
    val latitude: String,
    val longitude: String
) : Parcelable