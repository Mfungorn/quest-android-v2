package com.example.app.di

import com.example.app.di.modules.*
import com.example.app.features.MainActivity
import com.example.app.features.MainFlowFragment
import com.example.app.features.auth.data.AuthApi
import com.example.app.features.auth.data.TokenRepository
import com.example.app.features.auth.presentation.SignInFragment
import com.example.app.features.auth.presentation.SignUpFragment
import com.example.app.features.profile.data.UserApi
import com.example.app.features.profile.data.UserRepository
import com.example.app.features.profile.presentation.UserProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [
        ApiModule::class,
        SharedPreferencesModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        UserProfileViewModelModule::class,
        AuthViewModelModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFlowFragment: MainFlowFragment)
    fun inject(userProfileFragment: UserProfileFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)

    fun userApi(): UserApi
    fun userRepository(): UserRepository

    fun authApi(): AuthApi
    fun tokenRepository(): TokenRepository
}
