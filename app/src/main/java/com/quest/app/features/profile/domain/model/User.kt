package com.quest.app.features.profile.domain.model

import com.quest.app.features.quests.domain.model.Quest

data class User (
    val id: Long,
    val name: String,
    val login: String,
    val email: String,
    val imageUrl: String?,
    var clientToken: String?,
    val currentXp: Int,
    val level: Int,
    val quests: List<Quest>,
    val authoredQuests: List<Quest>,
    val subscribtions: List<User>?,
    val subscribers: List<User>?,
    val provider: String,
    var providerId: String? = null
)