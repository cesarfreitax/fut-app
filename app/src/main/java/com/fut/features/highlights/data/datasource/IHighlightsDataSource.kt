package com.fut.features.highlights.data.datasource

import com.fut.core.utils.ResponseWrapper
import com.fut.features.highlights.data.models.response.NewsResponse

interface IHighlightsDataSource {

    /**
     * Get news by query sorted by desc.
     *
     * Get a list with the selected query parameter
     *
     * @param query: The filtering keyword.
     * @param sortBy: Not required. The value already setted by default.
     * @return [NewsResponse] A list with all news from the query keyword.
     */
    suspend fun getEverything(
        query: String,
        sortBy: String
    ): ResponseWrapper<NewsResponse>
}