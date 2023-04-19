package com.fut.features.matches.data.repository

import com.fut.core.utils.ResponseWrapper
import com.fut.features.matches.data.datasource.IMatchesDataSource
import com.fut.features.matches.data.datasource.MatchesDataSourceImpl
import com.fut.features.matches.domain.FixturesEntity
import com.fut.features.matches.domain.IMatchesRepository
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val dataSource: IMatchesDataSource
): IMatchesRepository {

    override suspend fun getFixtures(
        timezone: String,
        date: String
    ): ResponseWrapper<FixturesEntity> {
        return when (val response = dataSource.getFixtures(timezone, date)) {
            is ResponseWrapper.Success -> {
                val entity = FixturesEntity.mapper(response.result!!)
                ResponseWrapper.Success(entity)
            }
            is ResponseWrapper.Error -> ResponseWrapper.Error(response.message ?: "")
        }
    }
}