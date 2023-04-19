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
class NewsApiNetworkModule {

    @Singleton
    @Provides
    @Named("ApiNewsOkHttpClient")
    fun provideApiNewsOkHttpClient(
        @Named("NewsLoggingInterceptor") loggingInterceptor: Interceptor,
        @Named("NewsHeadersInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("ApiNewsRetrofit")
    fun provideApiNewsRetrofit(
        @Named("ApiNewsOkHttpClient") client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.URL_NEWS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}