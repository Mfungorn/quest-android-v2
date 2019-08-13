package com.quest.app.di.modules

import androidx.lifecycle.ViewModelProvider
import com.quest.app.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}