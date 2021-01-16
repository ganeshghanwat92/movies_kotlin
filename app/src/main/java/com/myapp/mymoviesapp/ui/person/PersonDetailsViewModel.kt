package com.myapp.mymoviesapp.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.CoroutinesDispatcherProvider
import com.myapp.mymoviesapp.datamodel.people.Person
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(private val repository: Repository, private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {

    private var jobPersonDetails : CompletableJob? = null

    private val _personDetailsLiveData  = MutableLiveData<ResultWrapper<Person>>()

    val personDetailsLiveData : LiveData<ResultWrapper<Person>> get() = _personDetailsLiveData

    fun getPersonDetails(id: Int){

        jobPersonDetails = Job()

        _personDetailsLiveData.postValue(ResultWrapper.Loading(true))

        jobPersonDetails?.let {

            CoroutineScope(coroutinesDispatcherProvider.io + it).launch {

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