package com.example.fakerslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakerslist.model.PersonData

class PersonDetailsViewModel : ViewModel() {

    private val _personDetails = MutableLiveData<PersonData>()
    val personDetails: LiveData<PersonData>
        get() = _personDetails

    fun setPersonDetails(personData: PersonData) {
        _personDetails.value = personData
    }
}