package com.example.app.features.quests.data

import com.example.app.features.quests.domain.model.Quest
import com.example.app.features.quests.domain.model.QuestPostPayload
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface QuestsApi {
    @GET("/quests/my")
    fun loadUserQuests(): Single<List<Quest>>

    @GET("/quests/{id}")
    fun loadQuest(@Path("id") id: Long): Single<Quest>

    @GET("/quests")
    fun loadQuestsAuthoredBy(@Query("a") id: Long): Single<List<Quest>>

    @POST("/quests")
    fun sendQuest(@Query("t") targetId: Long, @Body questPostPayload: QuestPostPayload): Completable
}