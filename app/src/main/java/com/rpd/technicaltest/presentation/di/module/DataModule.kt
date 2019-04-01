package com.rpd.technicaltest.presentation.di.module

import com.google.gson.GsonBuilder
import com.rpd.technicaltest.BuildConfig
import com.rpd.technicaltest.data.Service
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {
    private val service: Service

    init {
        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.BUILD_TYPE == "debug") {
            okHttpBuilder.addInterceptor { chain ->
                println(chain.request())
                chain.proceed(chain.request())
            }
        }

        val okHttpClient = okHttpBuilder.build()

        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

        service = retrofit.create(Service::class.java)
    }

    @Provides
    @Singleton
    internal fun provideEndpoint(): Service = service
}