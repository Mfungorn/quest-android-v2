package com.quest.app.di

import com.quest.app.di.modules.*
import com.quest.app.features.LoginActivity
import com.quest.app.features.MainActivity
import com.quest.app.features.auth.data.AuthApi
import com.quest.app.features.auth.data.TokenRepository
import com.quest.app.features.auth.presentation.SignInFragment
import com.quest.app.features.auth.presentation.SignUpFragment
import com.quest.app.features.menu.MenuFragment
import com.quest.app.features.profile.data.UserApi
import com.quest.app.features.profile.data.UserRepository
import com.quest.app.features.profile.presentation.EditProfileFragment
import com.quest.app.features.profile.presentation.UserProfileFragment
import com.quest.app.features.quests.data.QuestsApi
import com.quest.app.features.quests.data.QuestsRepository
import com.quest.app.features.quests.presentation.QuestCreateFragment
import com.quest.app.features.quests.presentation.QuestDetailsFragment
import com.quest.app.features.quests.presentation.QuestsListFragment
import com.quest.app.features.subscribers.data.SubscribersApi
import com.quest.app.features.subscribers.data.SubscribersRepository
import com.quest.app.features.subscribers.presentation.SubscriberProfileFragment
import com.quest.app.features.subscribers.presentation.SubscribersFragment
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
    fun inject(questCreateFragment: QuestCreateFragment)
    fun inject(questDetailsFragment: QuestDetailsFragment)

    fun inject(subscribersFragment: SubscribersFragment)
    fun inject(subscriberProfileFragment: SubscriberProfileFragment)

    fun inject(menuFragment: MenuFragment)

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
