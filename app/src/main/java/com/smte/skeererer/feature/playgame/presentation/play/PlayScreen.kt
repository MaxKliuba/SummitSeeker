package com.smte.skeererer.feature.playgame.presentation.play

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smte.skeererer.R
import com.smte.skeererer.core.gillSansBoldFontFamily
import com.smte.skeererer.feature.playgame.presentation.components.BackIconButton
import com.smte.skeererer.feature.playgame.presentation.play.components.ArtifactComponent
import com.smte.skeererer.feature.playgame.presentation.play.components.GameBackgroundBox
import com.smte.skeererer.feature.playgame.presentation.play.components.GamePauseIconButton
import com.smte.skeererer.feature.playgame.presentation.play.components.GamePlayIconButton
import com.smte.skeererer.feature.playgame.presentation.play.components.PauseMenuComponent
import com.smte.skeererer.feature.playgame.presentation.play.components.PlayerComponent

@Composable
fun PlayScreen(
    onNavigateUp: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    val configuration = LocalConfiguration.current

    BackHandler {
        viewModel.saveScore()
        onNavigateUp()
    }

    GameBackgroundBox(
        originalOffset = state.gameState?.backgroundOffset1 ?: 0,
        mirroringOffset = state.gameState?.backgroundOffset2 ?: 0,
        contentModifier = Modifier.onGloballyPositioned {
            if (state.gameState == null && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewModel.playGame(it.size.width, it.size.height)
            }
        }
    ) {
        state.gameState?.let { gameState ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { viewModel.applyJump() }
                        )
                    }
            ) {
                gameState.artifacts.forEach { artifact ->
                    ArtifactComponent(artifact = artifact)
                }

                PlayerComponent(player = gameState.player)
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
            GamePlayIconButton(
                onClick = viewModel::resumeGame,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )

            PauseMenuComponent(
                onSaveScore = viewModel::saveScore,
                onNavigateToSettings = onNavigateToSettings,
                onNavigateUp = onNavigateUp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}