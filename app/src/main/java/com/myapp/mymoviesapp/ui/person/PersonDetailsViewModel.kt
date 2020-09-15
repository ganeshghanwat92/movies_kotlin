package com.myapp.mymoviesapp.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.people.Person
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*

class PersonDetailsViewModel(private val repository: Repository) : ViewModel() {

    private var jobPersonDetails : CompletableJob? = null

    private val _personDetailsLiveData  = MutableLiveData<ResultWrapper<Person>>()

    val personDetailsLiveData : LiveData<ResultWrapper<Person>> get() = _personDetailsLiveData

    fun getPersonDetails(id: Int){

        jobPersonDetails = Job()

        _personDetailsLiveData.postValue(ResultWrapper.Loading(true))

        jobPersonDetails?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _personDetailsLiveData.postValue(ResultWrapper.Loading(true))

                val res = repository.getPeopleDetails(id)

                _personDetailsLiveData.postValue(res)

            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        jobPersonDetails?.cancel()
    }

}