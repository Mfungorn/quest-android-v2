package com.example.app.features.quests.domain.model

import com.example.app.features.profile.domain.model.User

data class Quest(
    val id: Long,
    val title: String,
    val description: String,
    val xp: Int,
    val status: String,
    val author: User,
    val target: User,
    val steps: List<Step>,
    val awards: List<Award>
)