package com.fut.features.search.domain

import com.fut.core.utils.ResponseWrapper

interface ISearchRepository {

    suspend fun getTeamsByCountry(country: String) : ResponseWrapper<TeamsEntity>

    suspend fun getAllTeams() : ResponseWrapper<TeamsEntity>
}