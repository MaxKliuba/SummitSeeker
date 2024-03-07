package com.smte.skeererer.feature.playgame.presentation.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.R
import com.smte.skeererer.core.palameciaTitlingFontFamily

@Composable
fun MenuScreen(
    onNavigateToPlay: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToRatings: () -> Unit,
    onQuit: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.menu_title),
                fontFamily = palameciaTitlingFontFamily,
                fontSize = 60.sp,
                lineHeight = TextUnit(60f, TextUnitType.Sp),
                modifier = Modifier.padding(bottom = 60.dp)
            )

            GameMenuButton(
                text = stringResource(R.string.play_button),
                onClick = onNavigateToPlay,
                modifier = Modifier.padding(8.dp)
            )

            GameMenuButton(
                text = stringResource(R.string.settings_button),
                onClick = onNavigateToSettings,
                modifier = Modifier.padding(8.dp)
            )

            GameMenuButton(
                text = stringResource(R.string.ratings_button),
                onClick = onNavigateToRatings,
                modifier = Modifier.padding(8.dp)
            )

            GameMenuButton(
                text = stringResource(R.string.quit_button),
                onClick = onQuit,
                modifier = Modifier.padding(32.dp)
            )
        }
    }
}