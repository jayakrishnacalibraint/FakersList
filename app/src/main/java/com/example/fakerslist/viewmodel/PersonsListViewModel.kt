package com.example.fakerslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakerslist.ResponseState
import com.example.fakerslist.model.ApiResponse
import com.example.fakerslist.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsListViewModel @Inject constructor(private val personRepository: PersonRepository) : ViewModel() {
   private  val _personLivedata =MutableLiveData<ResponseState<ApiResponse>>()


    val personLiveData:LiveData<ResponseState<ApiResponse>>
        get() = _personLivedata

    fun fetchPersons() {
        viewModelScope.launch {
            val result: ResponseState<ApiResponse> = personRepository.getAllPersons(1000)
            _personLivedata.postValue(result)
        }
    }

}