package com.example.app.di.modules

import androidx.lifecycle.ViewModel
import com.example.app.di.ViewModelKey
import com.example.app.features.subscribers.presentation.SubscribersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SubscribersViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SubscribersViewModel::class)
    abstract fun bindMyViewModel(viewModel: SubscribersViewModel): ViewModel
}
