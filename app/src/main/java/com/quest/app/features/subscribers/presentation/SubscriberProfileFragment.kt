package com.quest.app.features.subscribers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentSubscriberProfileBinding
import com.quest.app.features.profile.domain.model.User
import com.quest.app.viewmodel.DaggerViewModelFactory
import kotlinx.android.synthetic.main.fragment_subscriber_profile.*
import javax.inject.Inject
import kotlin.math.roundToInt

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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.getParcelable<User>("subscriber")?.let {
            subscribeUi(it)
        }

        viewModel.subscribeSuccess.observe(this, Observer {
            findNavController().navigate(R.id.subscribersFragment)
        })
    }

    //    fun subscribeUi(subscriber: User?) = with(binding) {
//        if (subscriber != null) {
//            name = subscriber.name
//            email = subscriber.email
//            level = subscriber.level.toString()
//            val nextLevelXp = ((subscriber.level + 1) / 0.1) * ((subscriber.level + 1) / 0.1)
//            progress = ((subscriber.currentXp / nextLevelXp) * 100).roundToInt()
//        } else {
//            name = "No name"
//            email = "No email"
//            level = "?"
//            progress = 100
//        }
//        executePendingBindings()
//    }
    fun subscribeUi(subscriber: User?) = with(view) {
        if (subscriber != null) {
            userNameTextView.text = subscriber.name
            userEmailTextView.text = subscriber.email
            userLevelText.text = "Level: ${subscriber.level}"
            val nextLevelXp = ((subscriber.level + 1) / 0.1) * ((subscriber.level + 1) / 0.1)
            userXpProgress.progress = ((subscriber.currentXp / nextLevelXp) * 100).roundToInt()
        }
        subscribeButton.setOnClickListener {
            viewModel.subscribe()
        }
    }
}