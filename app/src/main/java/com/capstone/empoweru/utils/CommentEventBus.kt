package com.capstone.empoweru.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object CommentEventBus {
    private val _commentEventFlow = MutableSharedFlow<CommentEvent>()
    val commentEventFlow: SharedFlow<CommentEvent> = _commentEventFlow

    fun postEvent(event: CommentEvent) {
        _commentEventFlow.tryEmit(event)
    }
}

sealed class CommentEvent {
    object CommentAdded : CommentEvent()
}
