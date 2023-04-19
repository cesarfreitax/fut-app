package com.fut.features.userpref.data.datasource

import com.fut.core.utils.ResponseWrapper
import com.fut.features.userpref.data.models.response.CountriesResponse
import com.fut.features.userpref.data.models.response.LeaguesResponse

interface IUserPrefDataSource {

    /**
     * Get leagues by country
     *
     * Get a list with the selected country parameter
     *
     * @param country: The country's name.
     * @return [LeaguesResponse] A list with all leagues from the country name.
     */
    suspend fun getLeagues(): ResponseWrapper<LeaguesResponse>

    /**
     * Get countries
     *
     * Get a list with all contries
     *
     * @return [CountriesResponse] A list with all available countries.
     */
    suspend fun getCountries(): ResponseWrapper<CountriesResponse>
}