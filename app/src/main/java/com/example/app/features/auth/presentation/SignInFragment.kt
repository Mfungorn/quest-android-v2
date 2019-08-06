package com.example.app.features.auth.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.app.App
import com.example.app.R
import com.example.app.ui.DefaultTextWatcher
import com.example.app.utils.State
import com.example.app.viewmodel.DaggerViewModelFactory
import kotlinx.android.synthetic.main.fragment_signin.*
import javax.inject.Inject

class SignInFragment : Fragment() {
    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    lateinit var viewModel: AuthViewModel

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        viewModel = ViewModelProviders.of(this, this.viewModeFactory).get(AuthViewModel::class.java)

        viewModel.state.observe(this, Observer {
            when(it) {
                is State.Loading -> Log.d("Sign In", "Loading")
                is State.Error -> showMessage(it.message.toString())
            }
        })


        viewModel.signInDoneEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_signIn_to_userProfile)
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        signinButton.setOnClickListener {
            viewModel.signInUser()
        }

        signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }

        emailEditText.apply {
            addTextChangedListener(object : DefaultTextWatcher() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.request.email = s.toString()
                }
            })
            setText(viewModel.request.email)
        }

        passwordEditText.apply {
            addTextChangedListener(object : DefaultTextWatcher() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.request.password = s.toString()
                }
            })
            //setText(viewModel.request.password)
        }
    }

    fun getLayoutId() = R.layout.fragment_signin

    fun showMessage(m: String) {
        Toast.makeText(context, m, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = SignInFragment()
    }

}