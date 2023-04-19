package com.fut.core.network

import com.fut.core.utils.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun <T> doRequest(call: suspend () -> Response<@JvmSuppressWildcards T>): ResponseWrapper<@JvmSuppressWildcards T> =
    withContext(Dispatchers.IO) {
        try {
            val response = call()

            if (response.isSuccessful)
                ResponseWrapper.Success<@JvmSuppressWildcards T>(response.body()!!)
            else
                ResponseWrapper.Error(response.message())
        } catch (e: Exception) {
            ResponseWrapper.Error(e.message.orEmpty())
        }
    }

suspend fun <T> doCall(call: suspend () -> Call<@JvmSuppressWildcards T>): ResponseWrapper<@JvmSuppressWildcards T> =
    withContext(Dispatchers.IO) {

        val response = call()

        return@withContext suspendCoroutine<ResponseWrapper<@JvmSuppressWildcards T>> { continuation ->

            try {
                response.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, res: Response<T>) {
                        if (res.isSuccessful) {
                            continuation.resume(ResponseWrapper.Success<@JvmSuppressWildcards T>(res.body()!!))
                        } else {
                            continuation.resume(ResponseWrapper.Error<@JvmSuppressWildcards T>(res.message()))
                        }
                    }

                    override fun onFailure(call: Call<T>, t: Throwable) {
                        continuation.resume(ResponseWrapper.Error<@JvmSuppressWildcards T>(t.message.orEmpty()))
                    }
                })
            } catch (e: Exception) {
                continuation.resume(ResponseWrapper.Error(e.message.orEmpty()))
            }
        }
    }
