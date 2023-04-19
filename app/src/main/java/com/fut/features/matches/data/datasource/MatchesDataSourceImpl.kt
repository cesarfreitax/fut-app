package com.fut.features.matches.data.datasource

import com.fut.core.network.doRequest
import com.fut.core.utils.ResponseWrapper
import com.fut.features.matches.data.MatchesApiService
import com.fut.features.matches.data.models.response.FixturesResponse
import javax.inject.Inject

class MatchesDataSourceImpl @Inject constructor(
    private val apiService: MatchesApiService
) : IMatchesDataSource {

    override suspend fun getFixtures(
        timezone: String,
        date: String
    ): ResponseWrapper<FixturesResponse> =
        doRequest {
            apiService.getFixtures(timezone, date)
        }
}