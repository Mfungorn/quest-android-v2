package com.example.app.features.subscribers.data

import com.example.app.features.profile.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SubscribersDataSource @Inject constructor(
    override val api: SubscribersApi
) : SubscribersRepository {
    override fun loadSubscribtions(): Single<List<User>> {
        return api.loadSubscribtions()
    }

    override fun loadSubscribers(): Single<List<User>> {
        return api.loadSubscribers()
    }

    override fun sendSubscribeRequest(login: String): Completable {
        return api.sendSubscribeRequest(login)
    }

    override fun searchFriends(query: String): Single<List<User>> {
        return api.searchFriends(query)
    }
}
