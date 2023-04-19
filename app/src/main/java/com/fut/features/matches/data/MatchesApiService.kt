package com.fut.features.matches.data

import com.fut.features.matches.data.models.response.FixturesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApiService {

    @GET("/fixtures")
    suspend fun getFixtures(
        @Query("timezone") timezone: String,
        @Query("date") date: String
    ) : Response<FixturesResponse>

}