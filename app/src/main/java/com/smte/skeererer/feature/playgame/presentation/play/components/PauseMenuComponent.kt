package com.smte.skeererer.feature.playgame.presentation.play.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.presentation.components.MenuButton
import com.smte.skeererer.feature.playgame.presentation.components.OverlayComponent

@Composable
fun PauseMenuComponent(
    onSaveScore: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    OverlayComponent(modifier = modifier) {
        MenuButton(
            text = stringResource(R.string.settings_button),
            onClick = onNavigateToSettings,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )

        MenuButton(
            text = stringResource(R.string.exit_button),
            onClick = {
                onSaveScore()
                onNavigateUp()
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}