package com.quest.app.features.quests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentQuestDetailsBinding
import com.quest.app.features.quests.domain.model.Quest
import com.quest.app.ui.AwardAdapter
import com.quest.app.ui.StepAdapter
import com.quest.app.utils.State
import com.quest.app.viewmodel.DaggerViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class QuestDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: QuestsViewModel

    private lateinit var binding: FragmentQuestDetailsBinding

    private var stepAdapter: StepAdapter? = null
    private var awardAdapter: AwardAdapter? = null

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders
            .of(this, this.viewModeFactory)
            .get(QuestsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quest_details, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        stepAdapter = StepAdapter()
        binding.stepsList.adapter = stepAdapter

        awardAdapter = AwardAdapter()
        binding.awardsList.adapter = awardAdapter

        viewModel.detailedSteps.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is State.Success -> it.data?.let { steps -> stepAdapter?.setSteps(steps) }
            }
        })

        viewModel.detailedAwards.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is State.Success -> it.data?.let { awards -> awardAdapter?.setAwards(awards) }
            }
        })

        arguments?.getParcelable<Quest>("quest")?.let {
            binding.apply {
                quest = it
                title = it.title
                author = it.author.login
                date = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(it.date)
                description = it.description
                xp = it.xp.toString()
                executePendingBindings()
            }
        }

        return binding.root
    }
}