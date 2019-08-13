package com.example.app.features.quests.domain.model

data class QuestPostPayload(
    var title: String,
    var description: String,
    val steps: MutableList<Step>,
    val awards: MutableList<Award>
)