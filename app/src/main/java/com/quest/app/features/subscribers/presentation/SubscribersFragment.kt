package com.quest.app.features.subscribers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentSubscribersListBinding
import com.quest.app.features.profile.domain.model.User
import com.quest.app.ui.SubscriberClickCallback
import com.quest.app.ui.SubscribersAdapter
import com.quest.app.utils.State
import com.quest.app.viewmodel.DaggerViewModelFactory
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

        binding.friendsSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.isNullOrEmpty()) {
                        viewModel.receiveSubscribers()
                    } else {
                        viewModel.searchByQuery(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        viewModel.receiveSubscribers()
                    } else {
                        viewModel.searchByQuery(newText)
                    }
                    return true
                }
            })

        binding.searchFriendButton.setOnClickListener {
            binding.friendsSearch.isFocusable = true
            binding.friendsSearch.isIconified = false
        }

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