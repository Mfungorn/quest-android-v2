package com.quest.app.features.profile.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.quest.app.features.quests.domain.model.Quest

data class User (
    @JsonProperty(value = "id") val id: Long,
    @JsonProperty(value = "name") val name: String,
    @JsonProperty(value = "login") val login: String,
    @JsonProperty(value = "email") val email: String,
    @JsonProperty(value = "imageUrl") val imageUrl: String?,
    @JsonProperty(value = "clientToken") var clientToken: String?,
    @JsonProperty(value = "currentXp") val currentXp: Int,
    @JsonProperty(value = "level") val level: Int,
    @JsonProperty(value = "quests") val quests: List<Quest>,
    @JsonProperty(value = "authoredQuests") val authoredQuests: List<Long>,
    @JsonProperty(value = "subscribtions") val subscribtions: List<Long>?,
    @JsonProperty(value = "subscribers") val subscribers: List<Long>?,
    @JsonProperty(value = "provider") val provider: String,
    @JsonProperty(value = "providerId") var providerId: String? = null
)