package com.quest.app.di.modules

import androidx.lifecycle.ViewModel
import com.quest.app.di.ViewModelKey
import com.quest.app.features.quests.presentation.QuestsViewModel
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