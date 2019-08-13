package com.example.app.features.subscribers.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.features.profile.domain.model.User
import com.example.app.features.subscribers.data.SubscribersRepository
import com.example.app.utils.State
import io.reactivex.disposables.CompositeDisposable
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
        // disposable += (repository...)
    }

    fun subscribeOn(target: User) {

    }

    fun showProfile(subscriber: User) {
        _user.postValue(subscriber)
    }
}