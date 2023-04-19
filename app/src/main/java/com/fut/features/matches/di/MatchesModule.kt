package com.fut.features.matches.di

import com.fut.features.matches.data.MatchesApiService
import com.fut.features.matches.data.datasource.IMatchesDataSource
import com.fut.features.matches.data.datasource.MatchesDataSourceImpl
import com.fut.features.matches.data.repository.MatchesRepositoryImpl
import com.fut.features.matches.domain.IMatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MatchesModule {

    @Provides
    @Singleton
    fun provideService(@Named("ApiFootballRetrofit") retrofit: Retrofit): MatchesApiService =
        retrofit.create(MatchesApiService::class.java)

    @Provides
    @Singleton
    fun provideDataSource(apiService: MatchesApiService): IMatchesDataSource =
        MatchesDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideRepository(dataSource: MatchesDataSourceImpl): IMatchesRepository =
        MatchesRepositoryImpl(dataSource)
}