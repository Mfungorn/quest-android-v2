package com.quest.app.features.quests.data

import com.quest.app.features.quests.domain.model.Quest
import com.quest.app.features.quests.domain.model.QuestPostPayload
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class QuestsDataSource @Inject constructor(
    override val api: QuestsApi

) : QuestsRepository {
    override fun loadUserQuests(): Single<List<Quest>> {
        return api.loadUserQuests()
    }

    override fun loadQuest(id: Long): Single<Quest> {
        return api.loadQuest(id)
    }

    override fun loadQuestsAuthoredBy(id: Long): Single<List<Quest>> {
        return api.loadQuestsAuthoredBy(id)
    }

    override fun sendQuest(targetId: Long, questPostPayload: QuestPostPayload): Completable {
        return api.sendQuest(targetId, questPostPayload)
    }
}