package com.example.app.di.modules

import com.example.app.features.auth.data.AuthApi
import com.example.app.features.auth.data.TokenDataSource
import com.example.app.features.auth.data.TokenRepository
import com.example.app.features.profile.data.UserApi
import com.example.app.features.profile.data.UserDataSource
import com.example.app.features.profile.data.UserRepository
import com.example.app.features.quests.data.QuestsApi
import com.example.app.features.quests.data.QuestsDataSource
import com.example.app.features.quests.data.QuestsRepository
import com.example.app.features.subscribers.data.SubscribersApi
import com.example.app.features.subscribers.data.SubscribersDataSource
import com.example.app.features.subscribers.data.SubscribersRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [ApiModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun userApi(apiModule: Retrofit): UserApi {
        return apiModule.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun userRepository(api: UserApi): UserRepository {
        return UserDataSource(api)
    }

    @Singleton
    @Provides
    fun questsApi(apiModule: Retrofit): QuestsApi {
        return apiModule.create(QuestsApi::class.java)
    }

    @Singleton
    @Provides
    fun questsRepository(api: QuestsApi): QuestsRepository {
        return QuestsDataSource(api)
    }

    @Singleton
    @Provides
    fun subscribersApi(apiModule: Retrofit): SubscribersApi {
        return apiModule.create(SubscribersApi::class.java)
    }

    @Singleton
    @Provides
    fun subscribersRepository(api: SubscribersApi): SubscribersRepository {
        return SubscribersDataSource(api)
    }

    @Singleton
    @Provides
    fun authApi(apiModule: Retrofit): AuthApi {
        return apiModule.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun tokenRepository(api: AuthApi): TokenRepository {
        return TokenDataSource(api)
    }
}