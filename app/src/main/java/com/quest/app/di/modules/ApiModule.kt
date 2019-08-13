package com.quest.app.di.modules

import android.content.SharedPreferences
import com.quest.app.network.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module(includes = [SharedPreferencesModule::class])
class ApiModule {

    @Singleton
    @Provides
    internal fun provideApiModule(prefs: SharedPreferences): Retrofit {
        return Retrofit.Builder()
            //.addCallAdapterFactory(CoroutineCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(createClient(prefs))
            .build()
    }

    private fun createClient(prefs: SharedPreferences): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val authInterceptor = BasicAuthInterceptor(prefs)

        builder.addInterceptor(authInterceptor)
        builder.addNetworkInterceptor(logInterceptor)

        return builder.build()
    }

    companion object {
        private const val BASE_URL = "https://quest-server-sample.herokuapp.com"
    }
}
