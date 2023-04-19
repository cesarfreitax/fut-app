package com.fut.features.search.data.datasource

import com.fut.core.utils.ResponseWrapper
import com.fut.features.search.data.models.response.TeamsResponse

interface ISearchDataSource {

    /**
     * Get teams by country
     *
     * Get a list with the selected country parameter
     *
     * @param country: The country's name.
     * @return [TeamsResponse] A list with all teams from the country name.
     */
    suspend fun getTeamsByCountry(country: String): ResponseWrapper<TeamsResponse>

    /**
     * Get all teams
     *
     * Get a list with all teams
     *
     * @return [TeamsResponse] A list with all teams founded.
     */
    suspend fun getAllTeams(): ResponseWrapper<TeamsResponse>
}