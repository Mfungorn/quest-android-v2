package com.quest.app.features.profile.data

import com.quest.app.features.profile.domain.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("/user/profile")
    fun getUser(): Single<User>

    @GET("/user/{id}")
    fun getUserById(@Path("id") id: Long): Single<User>
}