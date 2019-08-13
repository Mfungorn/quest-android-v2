package com.example.app.features.quests.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.app.R
import com.example.app.features.profile.domain.model.User
import com.example.app.ui.SubscriberClickCallback
import com.example.app.ui.SubscribersAdapter
import kotlinx.android.synthetic.main.dialog_add_step.*
import kotlinx.android.synthetic.main.dialog_choose_target.*


class DialogChooseTarget(
    subscribersList: List<User>
) : DialogFragment() {

    private var listener: OnTargetSelectListener? = null

    private var adapter: SubscribersAdapter? = null

    private var target: User? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context.takeIf { it is OnTargetSelectListener } as? OnTargetSelectListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)

        adapter = SubscribersAdapter(object : SubscriberClickCallback {
            override fun onClick(user: User) {
                target = user
            }
        })
        targetList.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        addStepDialogButton.setOnClickListener {
            target?.let { target -> listener?.onTargetSelect(target) }
            this@DialogChooseTarget.dismiss()
        }

        closeDialogButton.setOnClickListener {
            this@DialogChooseTarget.dismiss()
        }

        return inflater.inflate(R.layout.dialog_choose_target, container, false)
    }
}