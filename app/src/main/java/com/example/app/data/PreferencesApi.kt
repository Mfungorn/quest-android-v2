package com.example.app.data

import android.content.SharedPreferences

class PreferencesApi {
    companion object {
        const val sharedPreferencesName = "tripple.me.prefsTags"

        enum class PrefNames { TOKEN }

        fun getJwt(prefs: SharedPreferences): String? {
            return  prefs.getString(Companion.PrefNames.TOKEN.name, null)
        }

        fun setJwt(prefs: SharedPreferences, token: String) {
            prefs.edit().putString(Companion.PrefNames.TOKEN.name, token).apply()
        }

        fun delData(prefs: SharedPreferences) {
            prefs.edit()
                .clear()
                .apply()
        }
    }
}