package com.rpd.technicaltest.presentation.ui.di.component

import com.rpd.technicaltest.mvp.di.scope.PerActivity
import com.rpd.technicaltest.presentation.di.component.ApplicationComponent
import com.rpd.technicaltest.presentation.ui.MainActivity
import com.rpd.technicaltest.presentation.ui.di.module.MainActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}