package com.quest.app.features.auth.domain.model

data class SignUpRequest(
    var name: String,
    var login: String,
    var email: String,
    var password: String
)