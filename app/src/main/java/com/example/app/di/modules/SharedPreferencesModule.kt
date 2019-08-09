package com.example.app.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.app.R
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class SharedPreferencesModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(
            context.resources.getString(R.string.SHARED_PREFS),
            Context.MODE_PRIVATE
        )
    }


}
