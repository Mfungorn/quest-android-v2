package com.quest.app.di.modules

import androidx.lifecycle.ViewModel
import com.quest.app.di.ViewModelKey
import com.quest.app.features.auth.presentation.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindMyViewModel(viewModel: AuthViewModel): ViewModel
}