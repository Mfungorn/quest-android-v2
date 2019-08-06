package com.example.app.features.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.app.App
import com.example.app.R
import com.example.app.databinding.FragmentProfileBinding
import com.example.app.features.profile.domain.model.User
import com.example.app.ui.SubscriberClickCallback
import com.example.app.ui.UserSubscribersAdapter
import com.example.app.utils.State
import com.example.app.viewmodel.DaggerViewModelFactory
import javax.inject.Inject


class UserProfileFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: UserProfileViewModel

    private lateinit var binding: FragmentProfileBinding

    lateinit var adapter: UserSubscribersAdapter

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(this, this.viewModeFactory).get(UserProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = UserSubscribersAdapter(object : SubscriberClickCallback{
            override fun onClick(user: User) {

            }
        })
        binding.subscribersList.adapter = adapter

        if (savedInstanceState == null) {
            viewModel.receiveUser()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(this, Observer{
            when(it) {
                is State.Success -> subscribeUi(it.data)
                is State.Error -> showMessage(it.message.toString())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
    }

    private fun subscribeUi(user: User?) {
        if (user != null) {
            binding.isLoading = false
            binding.name = user.name.takeIf { it.isBlank() } ?: "Name is empty"
            binding.email = user.email
            user.subscribers?.let { adapter.setSubscribers(it) }
        } else {
            binding.isLoading = true
            binding.name = "No name"
            binding.email = "No email"
        }
        binding.executePendingBindings()
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_profile

    companion object {
        fun newInstance() = UserProfileFragment()
    }
}