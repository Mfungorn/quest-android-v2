package com.example.app.features.subscribers.data

import com.example.app.features.profile.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SubscribersApi {
    @GET("/friends/subscribtions")
    fun loadSubscribtions(): Single<List<User>>

    @GET("/friends/subscribers")
    fun loadSubscribers(): Single<List<User>>

    @POST("/friends")
    fun sendSubscribeRequest(@Query("login") login: String): Completable

    @GET("/friends")
    fun searchFriends(@Query("q") query: String): Single<List<User>>
}