package com.quest.app.features.profile.data

import com.quest.app.features.profile.domain.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {
    @GET("/user/profile")
    fun getUser(): Single<User>
}