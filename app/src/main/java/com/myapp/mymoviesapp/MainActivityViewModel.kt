package com.myapp.mymoviesapp

import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.repository.Repository

class MainActivityViewModel(private val repo : Repository)  : ViewModel(){


    override fun onCleared() {
        super.onCleared()

    }

}