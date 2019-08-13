package com.quest.app.features

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.quest.app.App
import com.quest.app.R
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
        Log.d("MainActivity", "onCreate()")

        navController = Navigation.findNavController(this, getContainerId())
        Log.d("MainActivity", "Current destination: ${navController?.currentDestination?.navigatorName}")

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> navController?.navigate(R.id.userProfileFragment)
                R.id.quests -> navController?.navigate(R.id.questsListFragment)
                R.id.menu -> navController?.navigate(R.id.menuFragment)
            }
            return@setOnNavigationItemSelectedListener true
        }
        navigation.selectedItemId = R.id.quests
        navController?.navigate(R.id.questsListFragment)

    }

    private fun getLayoutId() = R.layout.activity_main

    private fun getContainerId() = R.id.container_main
}
