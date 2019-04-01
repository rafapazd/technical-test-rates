package com.rpd.technicaltest.presentation.di.component

import android.content.Context
import com.rpd.technicaltest.TechnicalTestApplication
import com.rpd.technicaltest.data.Service
import com.rpd.technicaltest.domain.rates.GetRatesUseCase
import com.rpd.technicaltest.presentation.di.module.ApplicationModule
import com.rpd.technicaltest.presentation.di.module.DataModule
import com.rpd.technicaltest.presentation.di.module.RepositoryModule
import com.rpd.technicaltest.presentation.di.module.UseCaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, RepositoryModule::class, UseCaseModule::class])
interface ApplicationComponent {
    fun inject(app: TechnicalTestApplication)
    fun getApplicationContext(): Context
    fun getEndpoint(): Service
    fun getRatesUseCase(): GetRatesUseCase
}
