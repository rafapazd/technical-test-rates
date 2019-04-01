package com.rpd.technicaltest.presentation.di.module

import com.rpd.technicaltest.data.Service
import com.rpd.technicaltest.data.rates.repository.RatesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    internal fun provideArticleRepository(service: Service): RatesRepository = RatesRepository(service)
}