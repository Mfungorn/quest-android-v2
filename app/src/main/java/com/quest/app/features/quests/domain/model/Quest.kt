package com.quest.app.features.quests.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class Quest(
    @JsonProperty(value = "id") val id: Long,
    @JsonProperty(value = "title") val title: String,
    @JsonProperty(value = "description") val description: String,
    @JsonProperty(value = "date") val date: Date,
    @JsonProperty(value = "xp") val xp: Int,
    @JsonProperty(value = "status") val status: String,
    @JsonProperty(value = "author") val author: Long,
    @JsonProperty(value = "target") val target: Long,
    @JsonProperty(value = "steps") val steps: List<Long>,
    @JsonProperty(value = "awards") val awards: List<Long>
)