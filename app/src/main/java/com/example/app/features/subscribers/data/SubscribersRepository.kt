package com.example.app.features.subscribers.data

import com.example.app.features.profile.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface SubscribersRepository {
    val api: SubscribersApi

    fun loadSubscribtions(): Single<List<User>>

    fun loadSubscribers(): Single<List<User>>

    fun sendSubscribeRequest(login: String): Completable

    fun searchFriends(query: String): Single<List<User>>
}