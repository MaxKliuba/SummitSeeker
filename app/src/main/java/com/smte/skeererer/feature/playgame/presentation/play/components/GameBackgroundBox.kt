package com.smte.skeererer.feature.playgame.presentation.play.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.smte.skeererer.R

@Composable
fun GameBackgroundBox(
    originalOffset: Int,
    mirroringOffset: Int,
    @SuppressLint("ModifierParameter") contentModifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier) {
        val gameBackgroundResId = R.drawable.game_background_light

        Image(
            painter = painterResource(id = gameBackgroundResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .absoluteOffset { IntOffset(originalOffset, 0) }
        )

        Image(
            painter = painterResource(id = gameBackgroundResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .absoluteOffset { IntOffset(mirroringOffset, 0) }
                .graphicsLayer(
                    scaleX = -1f, // Mirror horizontally
                    scaleY = 1f, // Keep the original vertical scale
                )
        )

        Box(
            modifier = contentModifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            content()
        }
    }
}