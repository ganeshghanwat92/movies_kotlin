package com.myapp.mymoviesapp.di.module

import com.myapp.mymoviesapp.di.scope.ActivityScope
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.ApiService
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(ApiClient.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService) : RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesRepository(remoteDataSource: RemoteDataSource) : Repository {
        return Repository(remoteDataSource)
    }

}