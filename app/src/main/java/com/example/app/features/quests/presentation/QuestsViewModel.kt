package com.example.app.features.quests.presentation

import androidx.lifecycle.ViewModel
import com.example.app.features.quests.data.QuestsRepository
import javax.inject.Inject

class QuestsViewModel @Inject constructor(
    private val repository: QuestsRepository
) : ViewModel() {

    fun cancel() {

    }
}