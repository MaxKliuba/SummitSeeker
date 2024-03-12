package com.smte.skeererer.feature.playgame.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.presentation.components.MenuBackgroundColumn
import com.smte.skeererer.feature.playgame.presentation.components.MenuButton

@Composable
fun MenuScreen(
    onNavigateToPlay: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToRatings: () -> Unit,
    onQuit: () -> Unit,
) {
    MenuBackgroundColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.app_title),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(vertical = 80.dp)
                .width(300.dp)
        )

        MenuButton(
            text = stringResource(R.string.start_button),
            onClick = onNavigateToPlay,
            modifier = Modifier.padding(8.dp)
        )

        MenuButton(
            text = stringResource(R.string.settings_button),
            onClick = onNavigateToSettings,
            modifier = Modifier.padding(8.dp)
        )

        MenuButton(
            text = stringResource(R.string.rating_button),
            onClick = onNavigateToRatings,
            modifier = Modifier.padding(8.dp)
        )

        MenuButton(
            text = stringResource(R.string.exit_button),
            onClick = onQuit,
            modifier = Modifier.padding(8.dp)
        )
    }
}