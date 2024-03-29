package com.quest.app.features.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.quest.app.App
import com.quest.app.R
import com.quest.app.ui.DefaultTextWatcher
import com.quest.app.utils.State
import com.quest.app.viewmodel.DaggerViewModelFactory
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject

class SignUpFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: AuthViewModel

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        viewModel = ViewModelProviders
            .of(this, this.viewModeFactory)
            .get(AuthViewModel::class.java)

        viewModel.state.observe(this, Observer {
            when(it) {
                is State.Error -> showMessage(it.message.toString())
            }
        })

        viewModel.signUpDoneEvent.observe(this, Observer {
            showMessage("Successfully registered")
            findNavController().navigate(R.id.action_signUp_to_signIn)
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nameEditText.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.request.name = s.toString()
            }
        })

        passwordEditText.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.request.password = s.toString()
            }

        })

        loginEditText.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.request.login = s.toString()
            }

        })

        emailEditText.addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.request.email = s.toString()
            }
        })

        registerButton.setOnClickListener {
            viewModel.signUpUser()
        }

        cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun getLayoutId() = R.layout.fragment_signup

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }

}
