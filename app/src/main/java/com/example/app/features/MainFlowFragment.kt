package com.example.app.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.App
import com.example.app.R
import com.example.app.features.profile.presentation.UserProfileFragment

class MainFlowFragment : Fragment() {

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        setHasOptionsMenu(true)

        childFragmentManager.beginTransaction()
            .addToBackStack("PROFILE")
            .add(getContainerId(), UserProfileFragment.newInstance(), "PROFILE")
            .commit()

        return view
    }

    private fun getLayoutId(): Int = R.layout.fragment_mainflow

    private fun getContainerId(): Int = R.id.flow_container


    companion object {
        private var instance : MainFlowFragment? = null
        fun newInstance() : MainFlowFragment {
            if (instance == null) instance =
                MainFlowFragment()
            return instance as MainFlowFragment
        }
    }
}