package com.quest.app.data

import android.content.SharedPreferences
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.quest.app.features.profile.domain.model.User
import org.json.JSONObject

class PreferencesApi {
    companion object {
        const val sharedPreferencesName = "tripple.me.prefsTags"

        enum class PrefNames { USER, TOKEN }

        fun getUser(prefs: SharedPreferences): User? {
            val jackson = ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(KotlinModule())
            val jsonString = prefs.getString(Companion.PrefNames.USER.name, null)
            if (jsonString.isNullOrBlank()) return null

            val jsonRoot = JSONObject(jsonString)
            return jackson.readValue(jsonRoot.toString(), User::class.java)
        }

        fun setUser(prefs: SharedPreferences, user: User) {
            val jackson = ObjectMapper()
            val json = jackson.writeValueAsString(user)
            prefs.edit().putString(Companion.PrefNames.USER.name, json).apply()
        }

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