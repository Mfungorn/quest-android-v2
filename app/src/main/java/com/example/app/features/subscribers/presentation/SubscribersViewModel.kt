package com.example.app.features.subscribers.presentation

import androidx.lifecycle.ViewModel
import com.example.app.features.subscribers.data.SubscribersRepository
import javax.inject.Inject

class SubscribersViewModel @Inject constructor(
    private val repository: SubscribersRepository
) : ViewModel() {

    fun cancel() {

    }
}