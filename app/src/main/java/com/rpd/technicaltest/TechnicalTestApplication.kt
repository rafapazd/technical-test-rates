package com.rpd.technicaltest

import android.app.Application
import com.rpd.technicaltest.presentation.di.component.ApplicationComponent
import com.rpd.technicaltest.presentation.di.component.DaggerApplicationComponent
import com.rpd.technicaltest.presentation.di.module.ApplicationModule

class TechnicalTestApplication : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initInjector()
    }

    private fun initInjector() {
        applicationComponent.inject(this)
    }
}