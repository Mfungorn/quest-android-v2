package com.quest.app.features.quests.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Step(
    @JsonProperty(value = "title") val title: String,
    @JsonProperty(value = "isReached") val isReached: Boolean
)