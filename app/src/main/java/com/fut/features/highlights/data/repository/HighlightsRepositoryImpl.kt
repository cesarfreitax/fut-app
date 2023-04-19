package com.fut.features.highlights.data.repository

import com.fut.core.utils.ResponseWrapper
import com.fut.features.highlights.data.datasource.HighlightsDataSourceImpl
import com.fut.features.highlights.data.datasource.IHighlightsDataSource
import com.fut.features.highlights.domain.IHighlightsRepository
import com.fut.features.highlights.domain.NewsEntity
import javax.inject.Inject

class HighlightsRepositoryImpl @Inject constructor(
    private val dataSource: IHighlightsDataSource
) : IHighlightsRepository {

    override suspend fun getEverything(
        query: String,
        sortBy: String
    ): ResponseWrapper<NewsEntity> {
        return when (val response = dataSource.getEverything(query, sortBy)) {
            is ResponseWrapper.Success -> {
                val entity = NewsEntity.mapper(response.result?.articles ?: arrayListOf())
                ResponseWrapper.Success(entity)
            }
            is ResponseWrapper.Error -> {
                ResponseWrapper.Error(response.message.orEmpty())
            }
        }
    }

}