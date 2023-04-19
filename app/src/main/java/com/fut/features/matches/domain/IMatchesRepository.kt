package com.fut.features.matches.domain

import com.fut.core.utils.ResponseWrapper
import java.util.Date

interface IMatchesRepository {

    suspend fun getFixtures(timezone: String, date: String) : ResponseWrapper<FixturesEntity>
}