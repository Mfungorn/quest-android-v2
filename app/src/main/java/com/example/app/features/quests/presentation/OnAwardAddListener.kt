package com.example.app.features.quests.presentation

import com.example.app.features.quests.domain.model.Award

interface OnAwardAddListener {
    fun onAwardAdd(award: Award)
}