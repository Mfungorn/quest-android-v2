package com.example.app.features.quests.presentation

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.data.PreferencesApi
import com.example.app.features.quests.data.QuestsRepository
import com.example.app.features.quests.domain.model.Quest
import com.example.app.features.quests.domain.model.QuestPostPayload
import com.example.app.utils.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QuestsViewModel @Inject constructor(
    private val repository: QuestsRepository,
    private val prefs: SharedPreferences
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _state = MutableLiveData<State<List<Quest>>>().apply {
        postValue(State.Loading())
    }
    val state: LiveData<State<List<Quest>>>
        get() = _state

    private val _detailedQuest: MutableLiveData<Quest> = MutableLiveData()
    val detailedQuest: LiveData<Quest>
        get() = _detailedQuest

    val newQuest = QuestPostPayload(
        "",
        "",
        mutableListOf(),
        mutableListOf()
    )

    val user = PreferencesApi.getUser(prefs)

    var targetUserId: Long? = null

    fun showQuestDetails(quest: Quest) {
        _detailedQuest.postValue(quest)
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
            }
        ))

    fun createQuest() {
        targetUserId?.let { target ->
            disposable += (repository.sendQuest(target, newQuest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = { t ->
                        _state.postValue(State.Error(t.toString()))
                    }
                ))
        }
    }

    fun cancel() {
        disposable.clear()
    }
}