package com.quest.app.ui

import com.quest.app.features.profile.domain.model.User

interface SubscriberClickCallback {
    fun onClick(user: User)
}