package com.smte.skeererer.feature.playgame.presentation.play.util

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.PointerInputScope
import kotlin.math.abs

suspend fun PointerInputScope.detectSwipe(
    swipeState: MutableState<SwipeDirection> = mutableStateOf(SwipeDirection.UNDEFINED),
    onSwipe: (SwipeDirection) -> Unit,
) = detectDragGestures(
    onDrag = { change, dragAmount ->
        change.consume()
        val (x, y) = dragAmount
        swipeState.value = if (abs(x) > abs(y)) {
            when {
                x > 0 -> SwipeDirection.RIGHT
                x < 0 -> SwipeDirection.LEFT
                else -> SwipeDirection.UNDEFINED
            }
        } else {
            when {
                y > 0 -> SwipeDirection.DOWN
                y < 0 -> SwipeDirection.UP
                else -> SwipeDirection.UNDEFINED
            }
        }
    },
    onDragEnd = {
        onSwipe(swipeState.value)
    },
    onDragCancel = {
        onSwipe(swipeState.value)
    }
)