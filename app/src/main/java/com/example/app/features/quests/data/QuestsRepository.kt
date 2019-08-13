package com.example.app.features.quests.data

import com.example.app.features.quests.domain.model.Quest
import com.example.app.features.quests.domain.model.QuestPostPayload
import io.reactivex.Completable
import io.reactivex.Single

interface QuestsRepository {
    val api: QuestsApi

    fun loadUserQuests(): Single<List<Quest>>

    fun loadQuest(id: Long): Single<Quest>

    fun loadQuestsAuthoredBy(id: Long): Single<List<Quest>>

    fun sendQuest(targetId: Long, questPostPayload: QuestPostPayload): Completable
}