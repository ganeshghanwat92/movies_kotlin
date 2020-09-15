package com.myapp.mymoviesapp.repository.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

   private const val baseUrl: String = "https://api.themoviedb.org/"


   private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy {

        retrofit.build()
            .create(ApiService::class.java)

    }


   private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    // set your desired log level

   private val httpClient by lazy {
        OkHttpClient.Builder().addInterceptor(logging)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
}
