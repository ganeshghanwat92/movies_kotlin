package com.myapp.mymoviesapp

import android.app.Activity
import android.view.inputmethod.InputMethodManager

object Utils {

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()?.getWindowToken(), 0)
    }

    fun buildPosterPath(posterPath : String?) : String{

        var path = posterPath

        if(path == null)
            path = "";

        val  baseUrl = "https://image.tmdb.org/t/p";
         val size = "/w780";

        return baseUrl+size+path;

    }

    fun buildBackdropPath(backdropPath : String?) : String{

        var path = backdropPath

        if(path == null)
            path = "";

        val  baseUrl = "https://image.tmdb.org/t/p";
        val size = "/original";

        return baseUrl+size+path;

    }

}