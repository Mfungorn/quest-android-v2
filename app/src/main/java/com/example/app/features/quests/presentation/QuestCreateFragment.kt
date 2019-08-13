package com.example.app.features.quests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.app.App
import com.example.app.R
import com.example.app.databinding.FragmentQuestCreateBinding
import com.example.app.features.profile.domain.model.User
import com.example.app.features.quests.domain.model.Award
import com.example.app.features.quests.domain.model.Step
import com.example.app.ui.AwardAdapter
import com.example.app.ui.DefaultTextWatcher
import com.example.app.ui.StepAdapter
import com.example.app.viewmodel.DaggerViewModelFactory
import javax.inject.Inject

class QuestCreateFragment : Fragment(),
    OnStepAddListener, OnAwardAddListener, OnTargetSelectListener {

    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: QuestsViewModel

    private lateinit var binding: FragmentQuestCreateBinding

    private var stepAdapter: StepAdapter? = null
    private var awardAdapter: AwardAdapter? = null

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

        stepAdapter = StepAdapter()
        binding.stepsList.adapter = stepAdapter

        awardAdapter = AwardAdapter()
        binding.awardsList.adapter = awardAdapter

        if (savedInstanceState != null) {
            binding.apply {
                newQuestTitle.setText(viewModel.newQuest.title)
                newQuestDescription.setText(viewModel.newQuest.description)
                stepAdapter?.setSteps(viewModel.newQuest.steps)
                awardAdapter?.setAwards(viewModel.newQuest.awards)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newQuestTitle.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.newQuest.title = s.toString()
            }
        })

        binding.newQuestDescription.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.newQuest.description = s.toString()
            }
        })

        binding.addStepButton.setOnClickListener {
            val addStepDialog = DialogAddStep()
            addStepDialog.show(childFragmentManager.beginTransaction(), "ADD_STEP")

        }

        binding.addAwardButton.setOnClickListener {
            val addAwardDialog = DialogAddAward()
            addAwardDialog.show(childFragmentManager.beginTransaction(), "ADD_AWARD")
        }

        binding.targetName.setOnClickListener {
            val targetSelectDialog = viewModel.user
                ?.subscribers
                ?.let { subs -> DialogChooseTarget(subs) }
            targetSelectDialog?.show(childFragmentManager.beginTransaction(), "SELECT_TARGET")
        }

        binding.questDoneButton.setOnClickListener {
            viewModel.createQuest()
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
    }

    override fun onStepAdd(step: Step) {
        viewModel.newQuest.steps.add(step)
        stepAdapter?.setSteps(viewModel.newQuest.steps)
    }

    override fun onAwardAdd(award: Award) {
        viewModel.newQuest.awards.add(award)
        awardAdapter?.setAwards(viewModel.newQuest.awards)
    }

    override fun onTargetSelect(target: User) {
        viewModel.targetUserId = target.id
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_quest_create

    companion object {
        fun newInstance() = QuestsListFragment()
    }
}