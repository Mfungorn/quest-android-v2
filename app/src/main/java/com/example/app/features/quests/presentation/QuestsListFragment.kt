package com.example.app.features.quests.presentation

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
import com.example.app.databinding.FragmentQuestsListBinding
import com.example.app.features.quests.domain.model.Quest
import com.example.app.ui.QuestAdapter
import com.example.app.ui.QuestClickCallback
import com.example.app.utils.State
import com.example.app.viewmodel.DaggerViewModelFactory
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
            }
        })
        binding.questsList.adapter = adapter

        binding.addQuestButton.setOnClickListener {
            findNavController().navigate(R.id.action_questsListFragment_to_questCreateFragment)
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

        viewModel.detailedQuest.observe(this, Observer {
            findNavController().navigate(R.id.action_questsListFragment_to_questDetailsFragment)
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