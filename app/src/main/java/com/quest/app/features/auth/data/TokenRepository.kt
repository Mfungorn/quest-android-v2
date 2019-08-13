package com.quest.app.features.auth.data

import com.quest.app.features.auth.domain.model.AuthResponse
import com.quest.app.features.auth.domain.model.LoginRequest
import com.quest.app.features.auth.domain.model.SignUpRequest
import io.reactivex.Completable
import io.reactivex.Single

interface TokenRepository {
    val api: AuthApi

    fun signInUserAndGetToken(loginRequest: LoginRequest): Single<AuthResponse>
    fun signUpUser(signUpRequest: SignUpRequest): Completable
}