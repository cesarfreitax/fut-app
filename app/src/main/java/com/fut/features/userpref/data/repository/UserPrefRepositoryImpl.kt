package com.fut.features.userpref.data.repository

import com.fut.core.utils.ResponseWrapper
import com.fut.features.userpref.data.datasource.UserPrefDataSourceImpl
import com.fut.features.userpref.domain.CountriesEntity
import com.fut.features.userpref.domain.IUserPrefRepository
import com.fut.features.userpref.domain.LeaguesEntity
import javax.inject.Inject

class UserPrefRepositoryImpl @Inject constructor(
    private val dataSource: UserPrefDataSourceImpl
) : IUserPrefRepository {

    override suspend fun getLeagues(): ResponseWrapper<LeaguesEntity> {
        return when(val response = dataSource.getLeagues()) {
            is ResponseWrapper.Success -> {
                val entity = LeaguesEntity.mapper(response.result?.response ?: arrayListOf())
                ResponseWrapper.Success(entity)
            }
            is ResponseWrapper.Error -> {
                ResponseWrapper.Error(response.message.orEmpty())
            }
        }
    }

    override suspend fun getCountries(): ResponseWrapper<CountriesEntity> {
        return when(val response = dataSource.getCountries()) {
            is ResponseWrapper.Success -> {
                val entity = CountriesEntity.mapper(response.result?.response ?: arrayListOf())
                ResponseWrapper.Success(entity)
            }
            is ResponseWrapper.Error -> {
                ResponseWrapper.Error(response.message.orEmpty())
            }
        }
    }
}