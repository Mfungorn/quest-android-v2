package com.quest.app.features.quests.presentation

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
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentQuestsListBinding
import com.quest.app.features.quests.domain.model.Quest
import com.quest.app.ui.QuestAdapter
import com.quest.app.ui.QuestClickCallback
import com.quest.app.utils.State
import com.quest.app.viewmodel.DaggerViewModelFactory
import javax.inject.Inject

class QuestsListFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: QuestsViewModel

    private lateinit var binding: FragmentQuestsListBinding

    private var adapter: QuestAdapter? = null

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
            .get(QuestsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = QuestAdapter(object : QuestClickCallback {
            override fun onClick(quest: Quest) {
                viewModel.showQuestDetails(quest)
                findNavController().navigate(
                    R.id.action_questsListFragment_to_questDetailsFragment,
                    viewModel.detailedQuest
                )
            }
        })
        binding.questsList.adapter = adapter

        binding.addQuestButton.setOnClickListener {
            viewModel.questCreationStart()
        }

        if (savedInstanceState == null) {
            viewModel.receiveQuests()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(this, Observer {
            when (it) {
                is State.Success -> subscribeUi(it.data)
                is State.Error -> showMessage(it.message.toString())
            }
        })

        viewModel.questSended.observe(this, Observer {
            when (it) {
                is State.Loading -> findNavController().navigate(R.id.action_questsListFragment_to_questCreateFragment)
                else -> {
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
    }

    private fun subscribeUi(list: List<Quest>?) {
        if (list != null) {
            adapter?.setQuests(list)
        } else {
            showMessage("Quests list is null")
        }
        binding.executePendingBindings()
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_quests_list

    companion object {
        fun newInstance() = QuestsListFragment()
    }
}