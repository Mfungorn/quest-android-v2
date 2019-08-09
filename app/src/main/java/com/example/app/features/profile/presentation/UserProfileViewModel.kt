package com.example.app.features.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.features.profile.data.UserRepository
import com.example.app.features.profile.domain.model.User
import com.example.app.utils.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            receiveUser()
        }
    }

    private val _state = MutableLiveData<State<User>>().apply {
        postValue(State.Loading())
    }

    val state: LiveData<State<User>>
        get() = _state
    val user: LiveData<User>
        get() = _user

    fun retry() {
        receiveUser()
    }

    fun cancel() {
        disposable.dispose()
    }

    fun receiveUser() = disposable.add(repository.loadUser()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onSuccess = { user ->
                _user.postValue(user)
                _state.postValue(State.Success(user))
            },
            onError = { t ->
                _state.postValue(State.Error(t.toString()))
            }
        ))

    // liveData = LiveDataReactiveStreams.fromPublisher(receiveUser())

//    private fun receiveUser() = viewModelScope.launch {
//        try {
//            repository.loadUserAsync()?.await()?.let {
//                _user.postValue(it)
//                _state.postValue(State.Success(it))
//            }
//        } catch (t: Throwable) {
//            _state.postValue(State.Error(t.message.toString()))
//        }
//    }
}