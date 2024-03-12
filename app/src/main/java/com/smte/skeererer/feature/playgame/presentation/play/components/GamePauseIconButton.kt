package com.smte.skeererer.feature.playgame.presentation.play.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.presentation.components.MenuIconButton

@Composable
fun GamePauseIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MenuIconButton(
        painter = painterResource(id = R.drawable.button_pause),
        contentDescription = stringResource(R.string.pause_button),
        onClick = onClick,
        modifier = modifier
    )
}