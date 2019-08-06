package com.example.app.di.modules

import com.example.app.features.auth.data.AuthApi
import com.example.app.features.auth.data.TokenDataSource
import com.example.app.features.auth.data.TokenRepository
import com.example.app.features.profile.data.UserApi
import com.example.app.features.profile.data.UserDataSource
import com.example.app.features.profile.data.UserRepository
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
    fun authApi(apiModule: Retrofit): AuthApi {
        return apiModule.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun tokenRepository(api: AuthApi): TokenRepository {
        return TokenDataSource(api)
    }
}