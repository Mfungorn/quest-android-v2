package com.quest.app.di.modules

import androidx.lifecycle.ViewModel
import com.quest.app.di.ViewModelKey
import com.quest.app.features.subscribers.presentation.SubscribersViewModel
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
