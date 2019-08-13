package com.example.app.features.quests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.app.R
import com.example.app.features.quests.domain.model.Step
import com.example.app.ui.DefaultTextWatcher
import kotlinx.android.synthetic.main.dialog_add_step.view.*


class DialogAddStep(
    private var listener: OnStepAddListener
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_step, container, false)

        var stepTitle = ""

        view.newStepName.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                stepTitle = s.toString()
            }
        })

        view.addStepDialogButton.setOnClickListener {
            listener.onStepAdd(Step(stepTitle, false))
            this@DialogAddStep.dismiss()
        }

        view.closeDialogButton.setOnClickListener {
            this@DialogAddStep.dismiss()
        }

        return view
    }
}