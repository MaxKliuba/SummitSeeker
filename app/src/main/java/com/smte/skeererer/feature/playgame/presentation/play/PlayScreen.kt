package com.smte.skeererer.feature.playgame.presentation.play

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smte.skeererer.core.palameciaTitlingFontFamily
import com.smte.skeererer.feature.playgame.presentation.play.util.SwipeDirection
import com.smte.skeererer.feature.playgame.presentation.play.util.detectSwipe

@Composable
fun PlayScreen(viewModel: PlayViewModel = hiltViewModel()) {
    val state by viewModel._uiState

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Score: 45",
            fontFamily = palameciaTitlingFontFamily,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectSwipe { direction ->
                        when (direction) {
                            SwipeDirection.LEFT -> viewModel.moveLeft()
                            SwipeDirection.RIGHT -> viewModel.moveRight()
                            else -> {}
                        }
                    }
                }
        ) {
            val playerSize = size.width / 3f
            val playerX = state * playerSize
            val playerY = size.height - playerSize * 1.5f

            drawRect(
                color = Color.Black,
                topLeft = Offset(playerX, playerY),
                size = Size(playerSize, playerSize),
            )
        }
    }
}