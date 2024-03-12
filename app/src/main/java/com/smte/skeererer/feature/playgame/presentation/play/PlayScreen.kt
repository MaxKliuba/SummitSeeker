package com.smte.skeererer.feature.playgame.presentation.play

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smte.skeererer.R
import com.smte.skeererer.core.gillSansBoldFontFamily
import com.smte.skeererer.feature.playgame.presentation.components.BackIconButton
import com.smte.skeererer.feature.playgame.presentation.components.MenuButton
import com.smte.skeererer.feature.playgame.presentation.play.components.GameBackgroundBox
import com.smte.skeererer.feature.playgame.presentation.play.components.GamePauseIconButton
import com.smte.skeererer.feature.playgame.presentation.play.util.SwipeDirection
import com.smte.skeererer.feature.playgame.presentation.play.util.detectSwipe

@Composable
fun PlayScreen(
    onNavigateUp: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    val configuration = LocalConfiguration.current

    GameBackgroundBox(
        isLight = true,
        contentModifier = Modifier.onGloballyPositioned {
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewModel.playGame(it.size.width, it.size.height)
            }
        }
    ) {
        state.let { state ->
            if (state is PlayUiState.Play) {
                val animatedPlayerX by animateFloatAsState(
                    targetValue = state.gameState.player.x.toFloat(),
                    label = "playerX"
                )

                val playerImage = ImageBitmap.imageResource(id = R.drawable.player_run)

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectSwipe { direction ->
                                when (direction) {
                                    SwipeDirection.UP -> viewModel.applyJump()
                                    else -> {}
                                }
                            }
                        }
                ) {
                    state.gameState.artifacts.forEach { barrier ->
                        drawRect(
                            color = Color.Green,
                            topLeft = Offset(barrier.x.toFloat(), barrier.y.toFloat()),
                            size = Size(barrier.sizeX.toFloat(), barrier.sizeY.toFloat()),
                        )
                    }


                    drawImage(
                        image = playerImage,
//                        srcOffset = IntOffset(1, 1),
                        srcSize = IntSize(
                            state.gameState.player.sizeX,
                            state.gameState.player.sizeY
                        ),
                    )
                }

                Text(
                    text = "Score: 45",
                    fontFamily = gillSansBoldFontFamily,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.TopCenter)
                )

                BackIconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                )

                GamePauseIconButton(
                    onClick = {
//                        viewModel.pauseGame()
//                        onNavigateToSettings()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
            } else if (state is PlayUiState.GameOver) {
                MenuButton(
                    text = "Restart",
                    onClick = viewModel::restartGame,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}