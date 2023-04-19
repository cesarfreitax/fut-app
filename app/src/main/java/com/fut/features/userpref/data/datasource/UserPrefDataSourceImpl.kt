package com.fut.features.userpref.data.datasource

import com.fut.core.network.doRequest
import com.fut.core.utils.ResponseWrapper
import com.fut.features.userpref.data.models.response.LeaguesResponse
import com.fut.features.userpref.data.UserPrefApiService
import com.fut.features.userpref.data.models.response.CountriesResponse
import javax.inject.Inject

class UserPrefDataSourceImpl @Inject constructor(
    private val apiService: UserPrefApiService
) : IUserPrefDataSource {

    override suspend fun getLeagues(): ResponseWrapper<LeaguesResponse> =
        doRequest {
            apiService.getLeagues()
        }

    override suspend fun getCountries(): ResponseWrapper<CountriesResponse> =
        doRequest {
            apiService.getCountries()
        }


}