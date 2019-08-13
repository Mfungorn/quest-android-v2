package com.example.app.features.quests.presentation

import com.example.app.features.quests.domain.model.Step

interface OnStepAddListener {
    fun onStepAdd(step: Step)
}