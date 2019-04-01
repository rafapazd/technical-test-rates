package com.rpd.technicaltest.presentation.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: Application) {
    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context = app
}
