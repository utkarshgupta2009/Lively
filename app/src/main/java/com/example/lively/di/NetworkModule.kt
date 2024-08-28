package com.example.lively.di

import com.example.lively.data.remote.ApiEndpoints
import com.example.lively.data.remote.RapidApiServices
import com.example.lively.utils.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun okHttp(): OkHttpClient{
        val loggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .build()
        }

    @Provides
    fun retroFit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiEndpoints.BASE_URL).build()
    }

    @Provides
    fun RapidApiServices(retrofit: Retrofit): RapidApiServices{

        return retrofit.create(RapidApiServices::class.java)

    }
    }

