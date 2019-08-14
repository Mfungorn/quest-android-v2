package com.quest.app.features.quests.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.quest.app.features.profile.domain.model.User
import java.util.*

data class Quest(
    @JsonProperty(value = "id") val id: Long,
    @JsonProperty(value = "title") val title: String,
    @JsonProperty(value = "description") val description: String,
    @JsonProperty(value = "date") val date: Date,
    @JsonProperty(value = "xp") val xp: Int,
    @JsonProperty(value = "status") val status: String,
    @JsonProperty(value = "author") val author: User,
    @JsonProperty(value = "target") val target: User,
    @JsonProperty(value = "steps") val steps: List<Step>,
    @JsonProperty(value = "awards") val awards: List<Award>
)