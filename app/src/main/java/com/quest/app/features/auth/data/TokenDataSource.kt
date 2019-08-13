package com.quest.app.features.auth.data

import com.quest.app.features.auth.domain.model.AuthResponse
import com.quest.app.features.auth.domain.model.LoginRequest
import com.quest.app.features.auth.domain.model.SignUpRequest
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TokenDataSource @Inject constructor(override val api: AuthApi) : TokenRepository {
    override fun signUpUser(signUpRequest: SignUpRequest): Completable {
        return api.signUpAsync(signUpRequest)
    }

    override fun signInUserAndGetToken(loginRequest: LoginRequest): Single<AuthResponse> {
        return api.signInUserAsync(loginRequest)
    }
}