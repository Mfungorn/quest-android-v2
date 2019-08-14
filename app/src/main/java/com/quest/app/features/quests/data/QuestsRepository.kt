package com.quest.app.features.quests.data

import com.quest.app.features.quests.domain.model.Award
import com.quest.app.features.quests.domain.model.Quest
import com.quest.app.features.quests.domain.model.QuestPostPayload
import com.quest.app.features.quests.domain.model.Step
import io.reactivex.Completable
import io.reactivex.Single

interface QuestsRepository {
    val api: QuestsApi

    fun loadUserQuests(): Single<List<Quest>>

    fun loadQuest(id: Long): Single<Quest>

    fun loadQuestsAuthoredBy(id: Long): Single<List<Quest>>

    fun sendQuest(targetId: Long, questPostPayload: QuestPostPayload): Completable

    fun loadQuestAwards(id: Long): Single<List<Award>>

    fun loadQuestSteps(id: Long): Single<List<Step>>
}