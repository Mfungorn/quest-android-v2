package com.example.app.features.subscribers.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.features.profile.domain.model.User
import com.example.app.features.subscribers.data.SubscribersRepository
import com.example.app.utils.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SubscribersViewModel @Inject constructor(
    private val repository: SubscribersRepository
) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun cancel() {
        disposable.clear()
    }

    private val _user: MutableLiveData<User> = MutableLiveData()

    private val _state = MutableLiveData<State<List<User>>>().apply {
        postValue(State.Loading())
    }

    val state: LiveData<State<List<User>>>
        get() = _state
    val user: LiveData<User>
        get() = _user

    fun receiveSubscribers() {
        disposable += (repository.loadSubscribers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _state.postValue(State.Success(it))
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                }
            ))
    }

    fun subscribeOn(target: User) {
        disposable += (repository.sendSubscribeRequest(target.login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                }
            ))
    }

    fun searchFriendsByLogin(login: String) {
        disposable += (repository.searchFriends(login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _state.postValue(State.Success(it))
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                }
            ))
    }

    fun showProfile(subscriber: User) {
        _user.postValue(subscriber)
    }
}