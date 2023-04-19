package com.fut.features.highlights.domain

import com.fut.core.utils.ResponseWrapper

interface IHighlightsRepository {

    suspend fun getEverything(
        query: String,
        sortBy: String
    ) : ResponseWrapper<NewsEntity>

}