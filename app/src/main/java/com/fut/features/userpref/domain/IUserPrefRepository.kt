package com.fut.features.userpref.domain

import com.fut.core.utils.ResponseWrapper

interface IUserPrefRepository {
    suspend fun getLeagues() : ResponseWrapper<LeaguesEntity>

    suspend fun getCountries() : ResponseWrapper<CountriesEntity>
}