package com.quest.app.features.auth.domain.model

class LoginRequest(
    private var email: String,
    private var password: String
) {
    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}