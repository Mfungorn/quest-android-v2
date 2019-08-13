package com.quest.app.ui

import com.quest.app.features.quests.domain.model.Quest

interface QuestClickCallback {
    fun onClick(quest: Quest)
}