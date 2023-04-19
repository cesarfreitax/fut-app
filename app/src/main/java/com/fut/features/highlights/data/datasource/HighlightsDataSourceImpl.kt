package com.fut.features.highlights.data.datasource

import com.fut.core.network.doCall
import com.fut.core.utils.ResponseWrapper
import com.fut.features.highlights.data.HighlightsApiService
import com.fut.features.highlights.data.models.response.NewsResponse
import javax.inject.Inject

class HighlightsDataSourceImpl @Inject constructor(
    private val apiService: HighlightsApiService
) : IHighlightsDataSource {

    override suspend fun getEverything(
        query: String,
        sortBy: String
    ): ResponseWrapper<NewsResponse> =
        doCall {
            (apiService.getEverything(query, sortBy))
        }
}