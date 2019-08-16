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
import kotlinx.android.synthetic.main.dialog_add_award.view.*


class DialogAddAward(
    private var listener: OnAwardAddListener
) : DialogFragment() {

    var awardName = ""
    var awardImageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_add_step, container, false)

        view.newAwardName.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                awardName = s.toString()
            }
        })

        view.newAwardImageUrl.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                awardImageUrl = s.toString()
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addAwardDialogButton.setOnClickListener {
            listener.onAwardAdd(Award(awardName, awardImageUrl))
            this@DialogAddAward.dismiss()
        }

        closeAwardDialogButton.setOnClickListener {
            this@DialogAddAward.dismiss()
        }
    }
}