package com.quest.app.features.subscribers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentSubscriberProfileBinding
import com.quest.app.viewmodel.DaggerViewModelFactory
import javax.inject.Inject

class SubscriberProfileFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: SubscribersViewModel

    private lateinit var binding: FragmentSubscriberProfileBinding

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
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_subscriber_profile, container, false
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.name = viewModel.subscriber.value?.name
        binding.email = viewModel.subscriber.value?.email
        // binding.level = viewModel.subscriber.value?.level

        binding.subscribeButton.setOnClickListener {
            viewModel.subscriber.value?.let { user -> viewModel.subscribeOn(user) }
        }

        return binding.root
    }
}