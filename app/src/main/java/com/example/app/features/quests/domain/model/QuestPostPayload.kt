package com.example.app.features.quests.domain.model

data class QuestPostPayload(
    val title: String,
    val description: String,
    val steps: MutableList<Step>,
    val awards: MutableList<Award>
)