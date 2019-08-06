package com.example.app.features.profile.domain.model

data class User (
    val id: Long,
    var name: String,
    var login: String,
    var email: String,
    var subscribtions: List<User>?,
    var subscribers: List<User>?,
    val provider: String,
    var providerId: String? = null
)