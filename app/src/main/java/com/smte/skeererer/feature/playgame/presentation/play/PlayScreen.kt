package com.smte.skeererer.feature.playgame.presentation.play

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smte.skeererer.R
import com.smte.skeererer.core.gillSansBoldFontFamily
import com.smte.skeererer.feature.playgame.domain.model.ArtifactType
import com.smte.skeererer.feature.playgame.presentation.components.BackIconButton
import com.smte.skeererer.feature.playgame.presentation.play.components.GameBackgroundBox
import com.smte.skeererer.feature.playgame.presentation.play.components.GamePauseIconButton
import com.smte.skeererer.feature.playgame.presentation.play.components.GamePlayIconButton
import com.smte.skeererer.feature.playgame.presentation.play.components.PauseMenuComponent

@Composable
fun PlayScreen(
    onNavigateUp: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    val configuration = LocalConfiguration.current

    val playerImage = ImageBitmap.imageResource(id = R.drawable.player_run)
    val coinImage = ImageBitmap.imageResource(id = R.drawable.artifact_coin)
    val starImage = ImageBitmap.imageResource(id = R.drawable.artifact_star)
    val heartImage = ImageBitmap.imageResource(id = R.drawable.artifact_heart)
    val diamondImage = ImageBitmap.imageResource(id = R.drawable.artifact_diamond)
    val rockImage = ImageBitmap.imageResource(id = R.drawable.artifact_rock)

    BackHandler {
        viewModel.saveScore()
        onNavigateUp()
    }

    GameBackgroundBox(
        originalOffset = state.gameState?.background?.x ?: 0,
        mirroringOffset = state.gameState?.background?.y ?: 0,
        contentModifier = Modifier
            .onGloballyPositioned {
                if (state.gameState == null && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    viewModel.startGame(it.size.width, it.size.height)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { viewModel.applyJump() }
                )
            }
    ) {
        state.gameState?.let { gameState ->
            val animatedPlayerY by animateIntAsState(
                targetValue = gameState.player.y,
                label = "playerY",
                animationSpec = spring(stiffness = Spring.StiffnessLow),
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawImage(
                    image = playerImage,
                    dstOffset = IntOffset(x = gameState.player.x, y = animatedPlayerY),
                    dstSize = IntSize(width = gameState.player.sizeX, gameState.player.sizeY)
                )

                gameState.artifacts.forEach { artifact ->
                    drawImage(
                        image = when (artifact.type) {
                            ArtifactType.COIN -> coinImage
                            ArtifactType.STAR -> starImage
                            ArtifactType.HEART -> heartImage
                            ArtifactType.DIAMOND -> diamondImage
                            ArtifactType.ROCK -> rockImage
                        },
                        dstOffset = IntOffset(x = artifact.x, y = artifact.y),
                        dstSize = IntSize(
                            width = artifact.sizeX,
                            height = artifact.sizeY,
                        )
                    )
                }
            }

            Text(
                text = stringResource(R.string.score_title, gameState.score),
                fontFamily = gillSansBoldFontFamily,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.TopCenter)
            )
        }

        BackIconButton(
            onClick = {
                viewModel.saveScore()
                onNavigateUp()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        if (state.gameState?.isRunning != false) {
            GamePauseIconButton(
                onClick = viewModel::pauseGame,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )
        } else {
            if (state.gameState?.isGameOver == false) {
                GamePlayIconButton(
                    onClick = viewModel::resumeGame,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
            }

            PauseMenuComponent(
                onSaveScore = viewModel::saveScore,
                onNavigateToSettings = onNavigateToSettings,
                onRestart = if (state.gameState?.isGameOver == true) viewModel::restartGame else null,
                onNavigateUp = onNavigateUp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}