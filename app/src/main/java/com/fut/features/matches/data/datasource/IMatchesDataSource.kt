package com.fut.features.matches.data.datasource

import com.fut.core.utils.ResponseWrapper
import com.fut.features.matches.data.models.response.FixturesResponse

interface IMatchesDataSource {

    /**
     * Get Brazilian Teams
     *
     * Get a list with the brazilian teams
     *
     * @param timezone: The timezone for hour compatibility.
     * @param date: The day to filter the fixtures.
     * @return [FixturesResponse] A list with all fixtures filtered.
     */
    suspend fun getFixtures(timezone: String, date: String): ResponseWrapper<FixturesResponse>
}