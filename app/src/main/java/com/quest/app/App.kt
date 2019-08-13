package com.quest.app

import android.app.Application
import android.content.Context
import com.quest.app.di.AppComponent
import com.quest.app.di.DaggerAppComponent
import com.quest.app.di.modules.ApiModule
import com.quest.app.di.modules.RepositoryModule
import com.quest.app.di.modules.SharedPreferencesModule

class App: Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = DaggerAppComponent.builder()
            .sharedPreferencesModule(SharedPreferencesModule(this))
            .apiModule(ApiModule())
            .repositoryModule(RepositoryModule())
            .build()

    }

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {

        private fun getApp(context: Context): App {
            return context.applicationContext as App
        }

        lateinit var INSTANCE: App


    }
}