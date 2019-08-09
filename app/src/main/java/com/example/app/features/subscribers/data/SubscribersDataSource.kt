package com.example.app.features.subscribers.data

import javax.inject.Inject

class SubscribersDataSource @Inject constructor(
    override val api: SubscribersApi
) : SubscribersRepository {
}