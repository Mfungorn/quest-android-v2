package com.quest.app.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.quest.app.R
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
