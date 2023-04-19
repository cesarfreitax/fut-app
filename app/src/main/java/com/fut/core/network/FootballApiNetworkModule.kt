package com.fut.core.network

import com.fut.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FootballApiNetworkModule {

    @Singleton
    @Provides
    @Named("ApiFootballOkHttpClient")
    fun provideApiFootballOkHttpClient(
        @Named("FootballLoggingInterceptor") loggingInterceptor: Interceptor,
        @Named("FootballHeadersInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("ApiFootballRetrofit")
    fun provideApiFootballRetrofit(
        @Named("ApiFootballOkHttpClient") client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.URL_FOOTBALL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}