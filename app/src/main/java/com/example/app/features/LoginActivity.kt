package com.example.app.features

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.app.App
import com.example.app.R
import com.example.app.data.PreferencesApi
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var prefs: SharedPreferences

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        navController = Navigation.findNavController(this, getContainerId())

        val token = PreferencesApi.getJwt(prefs)
        if (token.isNullOrEmpty()) {
            navController?.navigate(R.id.signInFragment)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getLayoutId() = R.layout.activity_login

    private fun getContainerId() = R.id.container_login
}