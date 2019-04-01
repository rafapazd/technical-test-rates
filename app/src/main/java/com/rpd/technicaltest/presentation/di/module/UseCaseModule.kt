package com.rpd.technicaltest.presentation.di.module

import com.rpd.technicaltest.data.rates.repository.RatesRepository
import com.rpd.technicaltest.domain.rates.GetRatesUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    @Named("ioScheduler")
    internal fun provideIoScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @Named("mainThreadScheduler")
    internal fun provideMainThreadScheduler() = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    internal fun provideGetRatesUseCase(ratesRepository: RatesRepository, @Named("ioScheduler") ioScheduler: Scheduler, @Named("mainThreadScheduler") mainThreadScheduler: Scheduler): GetRatesUseCase =
        GetRatesUseCase(ratesRepository, ioScheduler, mainThreadScheduler)
}