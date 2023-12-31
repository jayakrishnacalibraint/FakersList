package com.example.fakerslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakerslist.ResponseState
import com.example.fakerslist.model.PersonData
import com.example.fakerslist.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsListViewModel @Inject constructor(private val personRepository: PersonRepository) :
    ViewModel() {
    private val _personLivedata = MutableLiveData<ResponseState<List<PersonData>>>()


    val personLiveData: LiveData<ResponseState<List<PersonData>>>
        get() = _personLivedata

    fun fetchPersons(quantity: Int) {
        viewModelScope.launch {
            val result: ResponseState<List<PersonData>> = personRepository.getAllPersons(quantity)
            _personLivedata.postValue(result)
        }
    }

}