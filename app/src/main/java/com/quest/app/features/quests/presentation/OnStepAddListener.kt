package com.quest.app.features.quests.presentation

import com.quest.app.features.quests.domain.model.Step

interface OnStepAddListener {
    fun onStepAdd(step: Step)
}