package com.example.app.features.quests.data

import javax.inject.Inject

class QuestsDataSource @Inject constructor(
    override val api: QuestsApi

) : QuestsRepository {

}