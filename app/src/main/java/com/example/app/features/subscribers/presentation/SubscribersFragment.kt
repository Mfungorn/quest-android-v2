package com.example.app.features.subscribers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.app.App
import com.example.app.R
import com.example.app.databinding.FragmentSubscribersListBinding
import com.example.app.features.profile.domain.model.User
import com.example.app.ui.SubscriberClickCallback
import com.example.app.ui.SubscribersAdapter
import com.example.app.utils.RxSearch
import com.example.app.utils.State
import com.example.app.viewmodel.DaggerViewModelFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SubscribersFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: SubscribersViewModel

    private lateinit var binding: FragmentSubscribersListBinding

    private var adapter: SubscribersAdapter? = null

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders
            .of(this, this.viewModeFactory)
            .get(SubscribersViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = SubscribersAdapter(object : SubscriberClickCallback {
            override fun onClick(user: User) {
                viewModel.showProfile(user)
            }
        })
        binding.subscribersList.adapter = adapter

        RxSearch.fromView(binding.friendsSearch)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .switchMap()

        viewModel.state.observe(this, Observer {
            when (it) {
                is State.Success -> subscribeUi(it.data)
                is State.Error -> showMessage(it.message.toString())
            }
        })

        viewModel.user.observe(this, Observer {
            findNavController().navigate(R.id.action_subscribersFragment_to_subscriberProfileFragment)
        })

        if (savedInstanceState == null) {
            viewModel.receiveSubscribers()
        }

        return binding.root
    }

    private fun subscribeUi(list: List<User>?) = with(binding) {
        if (list != null) {
            if (list.isEmpty()) {
                emptyListText.visibility = View.VISIBLE
                subscribersList.visibility = View.GONE
            } else {
                emptyListText.visibility = View.GONE
                subscribersList.visibility = View.VISIBLE
                adapter?.setSubscribers(list)
            }
        } else {
            showMessage("Subscribers list is null")
        }
        executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_subscribers_list

    companion object {
        fun newInstance() = SubscribersFragment()
    }
}