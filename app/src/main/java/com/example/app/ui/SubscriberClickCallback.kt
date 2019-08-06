package com.example.app.ui

import com.example.app.features.profile.domain.model.User

interface SubscriberClickCallback {
    fun onClick(user: User)
}