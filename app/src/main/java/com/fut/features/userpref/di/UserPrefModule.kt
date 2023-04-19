package com.fut.features.userpref.di

import com.fut.features.userpref.data.UserPrefApiService
import com.fut.features.userpref.data.datasource.UserPrefDataSourceImpl
import com.fut.features.userpref.data.repository.UserPrefRepositoryImpl
import com.fut.features.userpref.domain.IUserPrefRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserPrefModule {

    @Provides
    @Singleton
    fun provideService(@Named("ApiFootballRetrofit") retrofit: Retrofit): UserPrefApiService =
        retrofit.create(UserPrefApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(dataSource: UserPrefDataSourceImpl): IUserPrefRepository =
        UserPrefRepositoryImpl(dataSource)
}