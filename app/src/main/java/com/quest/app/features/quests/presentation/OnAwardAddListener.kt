package com.quest.app.features.quests.presentation

import com.quest.app.features.quests.domain.model.Award

interface OnAwardAddListener {
    fun onAwardAdd(award: Award)
}