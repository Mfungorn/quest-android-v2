package com.example.app

import android.app.Application
import android.content.Context
import com.example.app.di.AppComponent
import com.example.app.di.DaggerAppComponent
import com.example.app.di.modules.ApiModule
import com.example.app.di.modules.RepositoryModule
import com.example.app.di.modules.SharedPreferencesModule

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