package com.fut.core.network


import android.os.Build
import com.fut.BuildConfig
import com.fut.core.CoreApplication
import com.fut.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsApiInterceptorModule {

    @Singleton
    @Provides
    @Named("NewsLoggingInterceptor")
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    @Named("NewsHeadersInterceptor")
    fun provideHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val userAgent = StringBuilder("Android ").append(Build.VERSION.SDK_INT).toString()
            val appName = CoreApplication.instance.applicationInfo.loadLabel(CoreApplication.instance.packageManager).toString()
            val newRequest = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", Constants.KEY_NEWS_API)
                .addHeader("X-Amz-User-Agent", userAgent)
                .addHeader("X-App-Version", BuildConfig.VERSION_NAME)
                .addHeader("App-Name", appName)
                .build()
            return@Interceptor chain.proceed(newRequest)
        }
    }
}
