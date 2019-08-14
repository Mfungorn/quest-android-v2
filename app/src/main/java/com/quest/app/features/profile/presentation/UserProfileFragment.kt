package com.quest.app.features.profile.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.quest.app.App
import com.quest.app.R
import com.quest.app.databinding.FragmentProfileBinding
import com.quest.app.features.profile.domain.model.User
import com.quest.app.utils.State
import com.quest.app.viewmodel.DaggerViewModelFactory
import javax.inject.Inject
import kotlin.math.roundToInt


class UserProfileFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: UserProfileViewModel

    private lateinit var binding: FragmentProfileBinding

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
            .get(UserProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            viewModel.receiveUser()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(this, Observer{
            when(it) {
                is State.Success -> subscribeUi(it.data)
                is State.Error -> showMessage(it.message.toString())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
    }

    private fun subscribeUi(user: User?) = with(binding) {
        if (user != null) {
            isLoading = false
            name = user.name
            email = user.email
            level = user.level.toString()
            if (!user.imageUrl.isNullOrEmpty()) {
                val decodedString = Base64.decode(user.imageUrl, Base64.DEFAULT)
                val decodedBitmap: Bitmap = BitmapFactory.decodeByteArray(
                    decodedString, 0, decodedString.size
                )
                profileImageView.setImageBitmap(decodedBitmap)
            }
            val nextLevelXp = ((user.level + 1) / 0.1) * ((user.level + 1) / 0.1)
            progress = ((user.currentXp / nextLevelXp) * 100).roundToInt()
        } else {
            isLoading = true
            name = "No name"
            email = "No email"
            level = "?"
            progress = 100
        }
        executePendingBindings()
    }

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    private fun getLayoutId() = R.layout.fragment_profile

    companion object {
        fun newInstance() = UserProfileFragment()
    }
}