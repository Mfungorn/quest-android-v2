package com.quest.app.features.quests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.quest.app.App
import com.quest.app.R
import com.quest.app.ui.AwardAdapter
import com.quest.app.ui.StepAdapter
import com.quest.app.viewmodel.DaggerViewModelFactory
import kotlinx.android.synthetic.main.fragment_quest_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class QuestDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: QuestsViewModel

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

        stepAdapter = StepAdapter()
        stepsList.adapter = stepAdapter

        awardAdapter = AwardAdapter()
        awardsList.adapter = awardAdapter

        viewModel.detailedQuest.value?.apply {
            questTitle.text = title
            questDescription.text = description
            authorName.text = author.name
            questDate.text = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(date)
            stepAdapter?.setSteps(steps)
            awardAdapter?.setAwards(awards)
            questXp.text = xp.toString()
        }

        return inflater.inflate(R.layout.fragment_quest_details, container, false)
    }
}