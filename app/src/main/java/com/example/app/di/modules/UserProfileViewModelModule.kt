package com.example.app.di.modules

import androidx.lifecycle.ViewModel
import com.example.app.di.ViewModelKey
import com.example.app.features.profile.presentation.UserProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindMyViewModel(viewModel: UserProfileViewModel): ViewModel
}