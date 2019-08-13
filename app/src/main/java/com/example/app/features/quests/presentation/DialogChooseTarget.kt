package com.example.app.features.quests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.app.R
import com.example.app.features.profile.domain.model.User
import com.example.app.ui.SubscriberClickCallback
import com.example.app.ui.SubscribersAdapter
import kotlinx.android.synthetic.main.dialog_choose_target.view.*

class DialogChooseTarget(
    val subscribersList: List<User>,
    private var listener: OnTargetSelectListener
) : DialogFragment() {

    private var adapter: SubscribersAdapter? = null

    private var target: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_choose_target, container, false)

        if (subscribersList.isEmpty()) {
            view.targetList.visibility = View.GONE
            view.emptyListText.visibility = View.VISIBLE
        } else {
            view.emptyListText.visibility = View.GONE
            view.targetList.visibility = View.VISIBLE
            adapter = SubscribersAdapter(object : SubscriberClickCallback {
                override fun onClick(user: User) {
                    listener.onTargetSelect(user)
                    dismiss()
                }
            })
            view.targetList.adapter = adapter
            adapter?.setSubscribers(subscribersList)
        }

        return view
    }
}