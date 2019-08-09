package com.example.app.di.modules

import androidx.lifecycle.ViewModel
import com.example.app.di.ViewModelKey
import com.example.app.features.quests.presentation.QuestsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class QuestsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(QuestsViewModel::class)
    abstract fun bindMyViewModel(viewModel: QuestsViewModel): ViewModel
}