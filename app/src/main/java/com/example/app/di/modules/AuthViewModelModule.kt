package com.example.app.di.modules

import androidx.lifecycle.ViewModel
import com.example.app.di.ViewModelKey
import com.example.app.features.auth.presentation.AuthViewModel
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