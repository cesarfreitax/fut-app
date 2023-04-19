package com.fut.features.search.di

import com.fut.features.search.data.SearchApiService
import com.fut.features.search.data.datasource.SearchDataSourceImpl
import com.fut.features.search.data.repository.SearchRepositoryImpl
import com.fut.features.search.domain.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideService(@Named("ApiFootballRetrofit") retrofit: Retrofit): SearchApiService =
        retrofit.create(SearchApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(dataSource: SearchDataSourceImpl): ISearchRepository =
        SearchRepositoryImpl(dataSource)

}