package com.example.app.features.profile.data

import com.example.app.features.profile.domain.model.User
import io.reactivex.Single

interface UserRepository {
    val api: UserApi

    fun loadUser(): Single<User>
}