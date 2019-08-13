package com.example.app.features

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.app.App
import com.example.app.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

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

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> navController?.navigate(R.id.userProfileFragment)
                R.id.quests -> navController?.navigate(R.id.questsListFragment)
                R.id.menu -> navController?.navigate(R.id.menuFragment)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun getLayoutId() = R.layout.activity_main

    private fun getContainerId() = R.id.container_main
}
