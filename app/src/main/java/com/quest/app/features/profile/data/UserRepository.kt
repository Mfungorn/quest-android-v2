package com.quest.app.features.profile.data

import com.quest.app.features.profile.domain.model.User
import io.reactivex.Single

interface UserRepository {
    val api: UserApi

    fun loadUser(): Single<User>
}