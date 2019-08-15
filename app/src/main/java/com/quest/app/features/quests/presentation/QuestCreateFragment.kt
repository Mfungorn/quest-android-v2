package com.quest.app.features.quests.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.HORIZONTAL
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentQuestCreateBinding
import com.quest.app.features.profile.domain.model.User
import com.quest.app.features.quests.domain.model.Award
import com.quest.app.features.quests.domain.model.Step
import com.quest.app.ui.AwardAdapter
import com.quest.app.ui.DefaultTextWatcher
import com.quest.app.ui.StepAdapter
import com.quest.app.viewmodel.DaggerViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
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
        binding.stepsList.apply {
            adapter = stepAdapter
            layoutManager = LinearLayoutManager(context)
        }

        awardAdapter = AwardAdapter()
        binding.awardsList.apply {
            adapter = awardAdapter
            layoutManager = StaggeredGridLayoutManager(3, HORIZONTAL)
        }

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

        viewModel.questSended.observe(this, Observer {
            findNavController().navigate(R.id.questsListFragment)
        })

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
            val addStepDialog = DialogAddStep(this)
            addStepDialog.show(childFragmentManager.beginTransaction(), "ADD_STEP")

        }

        binding.addAwardButton.setOnClickListener {
            val addAwardDialog = DialogAddAward(this)
            addAwardDialog.show(childFragmentManager.beginTransaction(), "ADD_AWARD")
        }

        binding.targetName.setOnClickListener {
            val targetSelectDialog = DialogChooseTarget(
                viewModel.targetsList,
                this
            )
            targetSelectDialog.show(childFragmentManager.beginTransaction(), "SELECT_TARGET")
        }

        binding.questDate.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener(function = { _, y, m, d ->
                    viewModel.date.set(y, m, d)
                    binding.questDate.text = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
                        .format(viewModel.date.time)
                }),
                viewModel.date.get(Calendar.YEAR),
                viewModel.date.get(Calendar.MONTH),
                viewModel.date.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.questDoneButton.setOnClickListener {
            viewModel.createQuest()
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
        binding.targetName.text = target.login
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_quest_create

    companion object {
        fun newInstance() = QuestsListFragment()
    }
}