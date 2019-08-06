package com.example.app.features.profile.data

import com.example.app.features.profile.domain.model.User
import io.reactivex.Single
import javax.inject.Inject

class UserDataSource @Inject constructor(override val api: UserApi) : UserRepository {
    override fun loadUser(): Single<User> {
        return api.getUser()
    }
}