package com.rpd.technicaltest.presentation.ui.di.module

import com.rpd.technicaltest.domain.rates.GetRatesUseCase
import com.rpd.technicaltest.mvp.di.scope.PerActivity
import com.rpd.technicaltest.presentation.ui.MainActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @PerActivity
    @Provides
    internal fun provideRatesPresenter(getRatesUseCase: GetRatesUseCase) =
        MainActivityPresenter(getRatesUseCase)
}
