package com.quest.app.features.quests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.quest.app.R
import com.quest.app.features.quests.domain.model.Award
import com.quest.app.ui.DefaultTextWatcher
import kotlinx.android.synthetic.main.dialog_add_award.*


class DialogAddAward(
    private var listener: OnAwardAddListener
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_add_step, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
            listener.onAwardAdd(Award(awardName, awardImageUrl))
            this@DialogAddAward.dismiss()
        }

        closeAwardDialogButton.setOnClickListener {
            this@DialogAddAward.dismiss()
        }
    }
}