package com.quest.app.features.subscribers.presentation

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quest.app.data.PreferencesApi
import com.quest.app.features.profile.domain.model.User
import com.quest.app.features.subscribers.data.SubscribersRepository
import com.quest.app.utils.SingleLiveEvent
import com.quest.app.utils.State
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SubscribersViewModel @Inject constructor(
    private val repository: SubscribersRepository,
    private val prefs: SharedPreferences
) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun cancel() {
        disposable.clear()
    }

    private val _state = MutableLiveData<State<List<User>>>().apply {
        postValue(State.Loading())
    }

    private val _subscribeSuccess = SingleLiveEvent<Unit>()
    val subscribeSuccess: LiveData<Unit> = _subscribeSuccess

    val state: LiveData<State<List<User>>>
        get() = _state
    var subscriber: Bundle? = null

    var targetLogin: String? = null

    private val user: User? by lazy {
        PreferencesApi.getUser(prefs)
    }

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

    fun subscribe() {
        targetLogin?.let {
            disposable += (repository.sendSubscribeRequest(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        _subscribeSuccess.call()
                    },
                    onError = { t ->
                        _state.postValue(State.Error(t.toString()))
                    }
                ))
        }
    }

    fun searchByQuery(query: String) {
        Single.just(query)
            .timeout(300, TimeUnit.MILLISECONDS)
            .flatMap { repository.searchFriends(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _state.postValue(State.Success(it.filter { s -> s.id != user?.id }))
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                }
            )
    }

//    fun searchFriendsByLogin(login: String) {
//        disposable += (repository.searchFriends(login)
//            .subscribe(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = {
//                    _state.postValue(State.Success(it))
//                },
//                onError = { t ->
//                    _state.postValue(State.Error(t.toString()))
//                }
//            ))
//    }

    fun showProfile(subscriber: User) {
        this.subscriber = bundleOf("subscriber" to subscriber)
        targetLogin = subscriber.login
    }
}