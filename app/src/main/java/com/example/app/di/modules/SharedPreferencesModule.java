package com.example.app.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.app.R;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SharedPreferencesModule {

    private Context context;

    public SharedPreferencesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference() {
        return context.getSharedPreferences(
                context.getResources().getString(R.string.SHARED_PREFS),
                Context.MODE_PRIVATE);
    }


}
