package com.smte.skeererer.feature.playgame.presentation.play

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.core.palameciaTitlingFontFamily
import com.smte.skeererer.feature.playgame.presentation.play.util.detectSwipe

@Composable
fun PlayScreen() {
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
                    detectSwipe {
                        println(it.name)
                    }
                }
        ) {

        }
    }
}