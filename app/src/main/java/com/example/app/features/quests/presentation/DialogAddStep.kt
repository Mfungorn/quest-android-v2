package com.example.app.features.quests.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.app.R
import com.example.app.features.quests.domain.model.Step
import com.example.app.ui.DefaultTextWatcher
import kotlinx.android.synthetic.main.dialog_add_step.*


class DialogAddStep : DialogFragment() {

    private var listener: OnStepAddListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context.takeIf { it is OnStepAddListener } as? OnStepAddListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var stepTitle = ""

        newStepName.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                stepTitle = s.toString()
            }
        })

        addStepDialogButton.setOnClickListener {
            listener?.onStepAdd(Step(stepTitle, false))
            this@DialogAddStep.dismiss()
        }

        closeDialogButton.setOnClickListener {
            this@DialogAddStep.dismiss()
        }

        return inflater.inflate(R.layout.dialog_add_step, container, false)
    }
}