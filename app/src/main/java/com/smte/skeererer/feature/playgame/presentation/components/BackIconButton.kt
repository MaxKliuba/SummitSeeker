package com.smte.skeererer.feature.playgame.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.smte.skeererer.R

@Composable
fun BackIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MenuIconButton(
        painter = painterResource(id = R.drawable.button_back),
        contentDescription = stringResource(id = R.string.back_button),
        onClick = onClick,
        modifier = modifier
    )
}