package com.quest.app.features.quests.presentation

import com.quest.app.features.profile.domain.model.User

interface OnTargetSelectListener {
    fun onTargetSelect(target: User)
}