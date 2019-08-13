package com.quest.app.features.menu

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.quest.app.App
import com.quest.app.R
import com.quest.app.data.PreferencesApi
import com.quest.app.features.LoginActivity
import kotlinx.android.synthetic.main.fragment_menu.view.*
import javax.inject.Inject

class MenuFragment : Fragment() {

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.subscribersButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_subscribersFragment)
        }
        view.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
        view.helpButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_helpFragment)
        }
        view.logoutButton.setOnClickListener {
            PreferencesApi.delData(prefs)
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}