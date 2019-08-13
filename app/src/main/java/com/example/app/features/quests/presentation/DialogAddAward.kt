package com.example.app.features.quests.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.app.R
import com.example.app.features.quests.domain.model.Award
import com.example.app.ui.DefaultTextWatcher
import kotlinx.android.synthetic.main.dialog_add_award.*


class DialogAddAward : DialogFragment() {

    private var listener: OnAwardAddListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context.takeIf { it is OnAwardAddListener } as? OnAwardAddListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var awardName = ""
        var awardImageUrl = ""

        newAwardName.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                awardName = s.toString()
            }
        })

        newAwardImageUrl.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                awardImageUrl = s.toString()
            }
        })

        addAwardDialogButton.setOnClickListener {
            listener?.onAwardAdd(Award(awardName, awardImageUrl))
            this@DialogAddAward.dismiss()
        }

        closeAwardDialogButton.setOnClickListener {
            this@DialogAddAward.dismiss()
        }

        return inflater.inflate(R.layout.dialog_add_step, container, false)
    }
}