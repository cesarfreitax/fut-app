package com.fut.features.highlights.data


import com.fut.features.highlights.data.models.response.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HighlightsApiService {

    @GET("/v2/everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String
    ): Call<NewsResponse>

}