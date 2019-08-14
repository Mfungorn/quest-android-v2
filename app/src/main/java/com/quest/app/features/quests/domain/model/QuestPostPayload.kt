package com.quest.app.features.quests.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class QuestPostPayload(
    @JsonProperty(value = "title") var title: String,
    @JsonProperty(value = "description") var description: String,
    @JsonProperty(value = "date") var date: Date,
    @JsonProperty(value = "steps") val steps: MutableList<Step>,
    @JsonProperty(value = "awards") val awards: MutableList<Award>
)