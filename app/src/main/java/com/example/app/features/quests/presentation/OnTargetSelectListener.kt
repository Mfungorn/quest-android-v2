package com.example.app.features.quests.presentation

import com.example.app.features.profile.domain.model.User

interface OnTargetSelectListener {
    fun onTargetSelect(target: User)
}