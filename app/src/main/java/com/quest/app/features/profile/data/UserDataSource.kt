package com.quest.app.features.profile.data

import com.quest.app.features.profile.domain.model.User
import io.reactivex.Single
import javax.inject.Inject

class UserDataSource @Inject constructor(
    override val api: UserApi
) : UserRepository {
    override fun loadUserById(id: Long): Single<User> {
        return api.getUserById(id)
    }

    override fun loadUser(): Single<User> {
        return api.getUser()
    }
}