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
import com.example.app.databinding.FragmentQuestsListBinding
import com.example.app.viewmodel.DaggerViewModelFactory
import javax.inject.Inject

class QuestsListFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: QuestsViewModel

    private lateinit var binding: FragmentQuestsListBinding

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

        if (savedInstanceState == null) {
            // viewModel.
        }

        binding.detailsButton.setOnClickListener {
            findNavController().navigate(R.id.action_questsListFragment_to_questDetailsFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_quests_list

    companion object {
        fun newInstance() = QuestsListFragment()
    }
}