package com.fut.features.search.data

import com.fut.features.search.data.models.response.TeamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("/teams")
    suspend fun getTeamsByCountry(
        @Query("country") country: String
    ) : Response<TeamsResponse>

    @GET("/teams")
    suspend fun getAllTeams() : Response<TeamsResponse>
}