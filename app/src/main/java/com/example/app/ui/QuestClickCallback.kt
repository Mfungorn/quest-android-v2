package com.example.app.ui

import com.example.app.features.quests.domain.model.Quest

interface QuestClickCallback {
    fun onClick(quest: Quest)
}