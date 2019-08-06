package com.example.app.features.auth.domain.model

class AuthResponse() {
    private var accessToken: String? = ""
    private var tokenType = "Bearer"

    fun getAccessToken(): String? {
        return accessToken
    }

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    fun getTokenType(): String {
        return tokenType
    }

    fun setTokenType(tokenType: String) {
        this.tokenType = tokenType
    }
}