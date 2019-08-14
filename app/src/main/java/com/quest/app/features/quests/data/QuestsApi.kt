package com.quest.app.features.quests.data

import com.quest.app.features.quests.domain.model.Award
import com.quest.app.features.quests.domain.model.Quest
import com.quest.app.features.quests.domain.model.QuestPostPayload
import com.quest.app.features.quests.domain.model.Step
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface QuestsApi {
    @GET("/quests/my")
    fun loadUserQuests(): Single<List<Quest>>

    @GET("/quests/{id}")
    fun loadQuest(@Path("id") id: Long): Single<Quest>

    @GET("/quests/authored")
    fun loadQuestsAuthoredBy(@Query("by") id: Long): Single<List<Quest>>

    @POST("/quests")
    fun sendQuest(@Query("targetId") targetId: Long, @Body questPostPayload: QuestPostPayload): Completable

    @GET("/quests/{id}/awards")
    fun loadQuestAwards(@Path("id") id: Long): Single<List<Award>>

    @GET("/quests/{id}/steps")
    fun loadQuestSteps(@Path("id") id: Long): Single<List<Step>>
}