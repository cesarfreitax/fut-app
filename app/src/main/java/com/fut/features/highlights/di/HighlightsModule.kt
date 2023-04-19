package com.fut.features.highlights.di

import com.fut.features.highlights.data.HighlightsApiService
import com.fut.features.highlights.data.datasource.HighlightsDataSourceImpl
import com.fut.features.highlights.data.datasource.IHighlightsDataSource
import com.fut.features.highlights.data.repository.HighlightsRepositoryImpl
import com.fut.features.highlights.domain.IHighlightsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HighlightsModule {

    @Provides
    @Singleton
    fun provideService(@Named("ApiNewsRetrofit") retrofit: Retrofit): HighlightsApiService =
        retrofit.create(HighlightsApiService::class.java)

    @Provides
    @Singleton
    fun provideDataSource(apiService: HighlightsApiService): IHighlightsDataSource =
        HighlightsDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideRepository(dataSource: HighlightsDataSourceImpl): IHighlightsRepository =
        HighlightsRepositoryImpl(dataSource)
}