package com.fut.features.search.data.datasource

import com.fut.core.network.doRequest
import com.fut.core.utils.ResponseWrapper
import com.fut.features.search.data.SearchApiService
import com.fut.features.search.data.models.response.TeamsResponse
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val apiService: SearchApiService
) : ISearchDataSource {

    override suspend fun getTeamsByCountry(country: String): ResponseWrapper<TeamsResponse> =
        doRequest {
            apiService.getTeamsByCountry(country)
        }

    override suspend fun getAllTeams(): ResponseWrapper<TeamsResponse> =
        doRequest {
            apiService.getAllTeams()
        }

}