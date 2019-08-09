package com.example.app.di

import com.example.app.di.modules.*
import com.example.app.features.LoginActivity
import com.example.app.features.MainActivity
import com.example.app.features.auth.data.AuthApi
import com.example.app.features.auth.data.TokenRepository
import com.example.app.features.auth.presentation.SignInFragment
import com.example.app.features.auth.presentation.SignUpFragment
import com.example.app.features.profile.data.UserApi
import com.example.app.features.profile.data.UserRepository
import com.example.app.features.profile.presentation.EditProfileFragment
import com.example.app.features.profile.presentation.UserProfileFragment
import com.example.app.features.quests.data.QuestsApi
import com.example.app.features.quests.data.QuestsRepository
import com.example.app.features.quests.presentation.QuestsListFragment
import com.example.app.features.subscribers.data.SubscribersApi
import com.example.app.features.subscribers.data.SubscribersRepository
import com.example.app.features.subscribers.presentation.SubscribersFragment
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
        SubscribersViewModelModule::class,
        QuestsViewModelModule::class,
        UserProfileViewModelModule::class,
        AuthViewModelModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)

    fun inject(userProfileFragment: UserProfileFragment)
    fun inject(editProfileFragment: EditProfileFragment)

    fun inject(questsListFragment: QuestsListFragment)

    fun inject(subscribersFragment: SubscribersFragment)

    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)

    fun questsApi(): QuestsApi
    fun questsRepository(): QuestsRepository

    fun subscribersApi(): SubscribersApi
    fun subscribersRepository(): SubscribersRepository

    fun userApi(): UserApi
    fun userRepository(): UserRepository

    fun authApi(): AuthApi
    fun tokenRepository(): TokenRepository
}
