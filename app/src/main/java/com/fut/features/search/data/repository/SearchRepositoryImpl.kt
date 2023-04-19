package com.fut.features.search.data.repository

import com.fut.core.utils.ResponseWrapper
import com.fut.features.search.data.datasource.SearchDataSourceImpl
import com.fut.features.search.domain.TeamsEntity
import com.fut.features.search.domain.ISearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSourceImpl
) : ISearchRepository {

    override suspend fun getTeamsByCountry(country: String): ResponseWrapper<TeamsEntity> {
        return when (val response = dataSource.getTeamsByCountry(country)) {
            is ResponseWrapper.Success -> {
                val entity = TeamsEntity.mapper(response.result!!)
                ResponseWrapper.Success(entity)
            }
            is ResponseWrapper.Error -> ResponseWrapper.Error(response.message ?: "")

        }
    }

    override suspend fun getAllTeams(): ResponseWrapper<TeamsEntity> {
        return when (val response = dataSource.getAllTeams()) {
            is ResponseWrapper.Success -> {
                val entity = TeamsEntity.mapper(response.result!!)
                ResponseWrapper.Success(entity)
            }
            is ResponseWrapper.Error -> ResponseWrapper.Error(response.message.orEmpty())

        }
    }
}