package com.smte.skeererer.feature.playgame.presentation.play.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.presentation.components.OverlayComponent

@Composable
fun PauseMenuComponent(
    onSaveScore: () -> Unit,
    onRestart: (() -> Unit)?,
    onNavigateToSettings: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    OverlayComponent(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            onRestart?.let { onRestart ->
                PauseMenuButton(
                    text = stringResource(R.string.restart_button),
                    onClick = onRestart,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )
            }

            PauseMenuButton(
                text = stringResource(R.string.settings_button),
                onClick = onNavigateToSettings,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )

            PauseMenuButton(
                text = stringResource(R.string.exit_button),
                onClick = {
                    onSaveScore()
                    onNavigateUp()
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}