package com.smte.skeererer.feature.playgame.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.R
import com.smte.skeererer.core.palameciaTitlingFontFamily
import com.smte.skeererer.feature.playgame.presentation.components.GameMenuButton

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.settings_title),
            fontFamily = palameciaTitlingFontFamily,
            fontSize = 56.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            // TODO
        }

        GameMenuButton(
            text = stringResource(R.string.back_button),
            onClick = onNavigateUp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp)
        )
    }
}