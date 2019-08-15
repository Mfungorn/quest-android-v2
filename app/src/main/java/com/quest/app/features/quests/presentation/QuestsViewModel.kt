package com.quest.app.features.quests.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quest.app.data.PreferencesApi
import com.quest.app.features.profile.domain.model.User
import com.quest.app.features.quests.data.QuestsRepository
import com.quest.app.features.quests.domain.model.Award
import com.quest.app.features.quests.domain.model.Quest
import com.quest.app.features.quests.domain.model.QuestPostPayload
import com.quest.app.features.quests.domain.model.Step
import com.quest.app.features.subscribers.data.SubscribersRepository
import com.quest.app.utils.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class QuestsViewModel @Inject constructor(
    private val repository: QuestsRepository,
    private val prefs: SharedPreferences,
    private val subscribersRepository: SubscribersRepository
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _state = MutableLiveData<State<List<Quest>>>().apply {
        postValue(State.Loading())
    }
    val state: LiveData<State<List<Quest>>>
        get() = _state

    private val _questSended = MutableLiveData<State<String>>()
    val questSended: LiveData<State<String>>
        get() = _questSended

    var detailedQuest: Bundle? = null
    val detailedSteps: MutableLiveData<State<List<Step>>> = MutableLiveData()
    val detailedAwards: MutableLiveData<State<List<Award>>> = MutableLiveData()

    var date: Calendar = Calendar.getInstance()
    val newQuest = QuestPostPayload(
        "",
        "",
        date.time,
        mutableListOf(),
        mutableListOf()
    )

    val user: User? by lazy {
        PreferencesApi.getUser(prefs)
    }
    var subscribers: List<User>? = null

    val targetsList: MutableList<User> by lazy {
        val list = mutableListOf<User>()
        user?.let {
            list.add(it)
            subscribers?.let { subs -> list.addAll(subs) }
        }
        list
    }

    var targetUserId: Long? = null

    fun showQuestDetails(quest: Quest) {
        getQuestAwards(quest.id)
        getQuestSteps(quest.id)
        detailedQuest = bundleOf("quest" to quest)
    }

    fun getQuestAwards(id: Long) {
        disposable.add(repository.loadQuestAwards(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    detailedAwards.postValue(State.Success(it))
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                    Log.e("QuestsViewModel", t.message)

                }
            ))
    }

    fun getQuestSteps(id: Long) {
        disposable.add(repository.loadQuestSteps(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    detailedSteps.postValue(State.Success(it))
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                    Log.e("QuestsViewModel", t.message)

                }
            ))
    }

    fun getSubscribers() {
        disposable.add(subscribersRepository.loadSubscribers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    subscribers = it
                    _questSended.postValue(State.Loading())
                },
                onError = { t ->
                    _state.postValue(State.Error(t.toString()))
                    Log.e("QuestsViewModel", t.message)

                }
            ))
    }

    fun receiveQuests() = disposable.add(repository.loadUserQuests()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onSuccess = {
                _state.postValue(State.Success(it))
            },
            onError = { t ->
                _state.postValue(State.Error(t.toString()))
                Log.e("QuestsViewModel", t.message)

            }
        ))

    fun questCreationStart() {
        getSubscribers()
    }

    fun createQuest() {
        targetUserId?.let { target ->
            disposable += (repository.sendQuest(target, newQuest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        _questSended.postValue(State.Success("done"))
                    },
                    onError = { t ->
                        _state.postValue(State.Error(t.toString()))
                        Log.e("QuestsViewModel", t.message)
                    }
                ))
        }
    }

    fun cancel() {
        disposable.clear()
    }
}