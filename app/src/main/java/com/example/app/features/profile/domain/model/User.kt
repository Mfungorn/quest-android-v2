package com.example.app.features.profile.domain.model

import com.example.app.features.quests.domain.model.Quest

data class User (
    val id: Long,
    var name: String,
    var login: String,
    var email: String,
    val quests: List<Quest>,
    val authoredQuests: List<Quest>,
    val subscribtions: List<User>?,
    val subscribers: List<User>?,
    val provider: String,
    var providerId: String? = null
)