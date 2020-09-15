package com.myapp.mymoviesapp.repository.remote

import com.myapp.mymoviesapp.repository.ResultWrapper
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): ResultWrapper<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResultWrapper.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString(),e)
        }
    }

    private fun <T> error(message: String,e : Exception): ResultWrapper<T> {
       // Timber.e(message)
        return ResultWrapper.Error("Network call has failed for a following reason: $message",e)
    }

}