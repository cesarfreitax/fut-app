package com.fut.features.userpref.data

import com.fut.features.userpref.data.models.response.CountriesResponse
import com.fut.features.userpref.data.models.response.LeaguesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserPrefApiService {

    @GET("/leagues")
    suspend fun getLeagues() : Response<LeaguesResponse>

    @GET("/countries")
    suspend fun getCountries() : Response<CountriesResponse>
}