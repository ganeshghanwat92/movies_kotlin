package com.myapp.mymoviesapp.di.module

import android.view.inputmethod.BaseInputConnection
import com.myapp.mymoviesapp.Constants
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.remote.ApiService
import com.myapp.mymoviesapp.repository.remote.BaseUrlInterceptor
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(okHttpClient)
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
    fun provideRemoteDataSource(apiService: ApiService, baseUrlInterceptor: BaseUrlInterceptor) : RemoteDataSource {
        return RemoteDataSource(apiService, baseUrlInterceptor)
    }

    @Provides
    @Singleton
    fun providesRepository(remoteDataSource: RemoteDataSource) : Repository {
        return Repository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideInterceptor() : BaseUrlInterceptor {
        return BaseUrlInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(baseUrlInterceptor: BaseUrlInterceptor) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(baseUrlInterceptor).build()
    }

}