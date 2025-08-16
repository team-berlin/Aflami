package com.berlin.aflami.viewmodel.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListCountEventBus @Inject constructor() {
    data class Delta(val listId: Int, val delta: Int)

    private val _events = MutableSharedFlow<Delta>(extraBufferCapacity = 64)
    val events: SharedFlow<Delta> = _events.asSharedFlow()

    suspend fun emit(listId: Int, delta: Int) {
        _events.emit(Delta(listId, delta))
    }
}