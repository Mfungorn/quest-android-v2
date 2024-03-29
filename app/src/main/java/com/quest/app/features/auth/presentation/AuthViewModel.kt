package com.quest.app.features.auth.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quest.app.data.PreferencesApi
import com.quest.app.features.auth.data.TokenRepository
import com.quest.app.features.auth.domain.model.LoginRequest
import com.quest.app.features.auth.domain.model.SignUpRequest
import com.quest.app.features.profile.data.UserRepository
import com.quest.app.utils.SingleLiveEvent
import com.quest.app.utils.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthViewModel @Inject constructor(
    private val repository: TokenRepository,
    private val userRepository: UserRepository,
    private val prefs: SharedPreferences
) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val _tokenReceivedEvent = SingleLiveEvent<Unit>()
    private val _userRegisteredEvent = SingleLiveEvent<Unit>()

    private val _state = MutableLiveData<State<String>>().apply {
        postValue(State.Loading())
    }

    val state: LiveData<State<String>>
        get() = _state

    val signInDoneEvent: LiveData<Unit>
        get() = _tokenReceivedEvent

    val signUpDoneEvent: LiveData<Unit>
        get() = _userRegisteredEvent

    var request = SignUpRequest( // TODO: Fix empty name
        name = "",
        login = "",
        email = "",
        password = ""
    )

    fun cancel() {
        disposable.clear()
    }

    fun signInUser() = disposable.add(
        repository.signInUserAndGetToken(LoginRequest(request.email, request.password))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onSuccess = {
                PreferencesApi.setJwt(prefs, "${it.getTokenType()} ${it.getAccessToken()}")
                _state.postValue(State.Success(it.getTokenType()))
                _tokenReceivedEvent.call()
            },
            onError = {
                _state.postValue(State.Error(it.message.toString()))
            }
        ))

    fun signUpUser() {
        disposable += (repository.signUpUser(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _userRegisteredEvent.call()
                },
                onError = {
                    _state.postValue(State.Error(it.message.toString()))
                }
            ))
    }

    fun loadUser() {
        disposable.add(userRepository.loadUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { user ->
                    PreferencesApi.setUser(prefs, user)
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                    Log.e("AuthViewModel", t.message)
                }
            ))
    }

//    fun signInUser() = viewModelScope.launch {
//        try {
//            repository.signInUserAndGetToken(LoginRequest(request.name, request.password)).await().also {
//                PreferencesApi.setJwt(prefs, "${it.getTokenType()} ${it.getAccessToken()}")
//                _state.postValue(State.Success(it.getTokenType()))
//                _tokenReceivedEvent.call()
//            }
//        } catch (t: Throwable) {
//            _state.postValue(State.Error(t.message.toString()))
//        }
//    }
//
//    fun signUpUser() = viewModelScope.launch {
//        try {
//            repository.signUpUser(request).await().also {
//                _userRegisteredEvent.call()
//            }
//        } catch (t: Throwable) {
//            _state.postValue(State.Error(t.message.toString()))
//        }
//    }
}