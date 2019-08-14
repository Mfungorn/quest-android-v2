package com.quest.app.features.quests.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Award(
    @JsonProperty("name") val name: String,
    @JsonProperty(value = "imageUrl") val imageUrl: String
)