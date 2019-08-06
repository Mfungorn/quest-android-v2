package com.example.app.features.profile.data

import com.example.app.features.profile.domain.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {
    @GET("/user/profile")
    fun getUser(): Single<User>
}