package com.smte.skeererer.feature.playgame.presentation.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smte.skeererer.R
import com.smte.skeererer.core.gillSansBoldFontFamily
import com.smte.skeererer.feature.playgame.presentation.components.BackIconButton
import com.smte.skeererer.feature.playgame.presentation.components.MenuBackgroundColumn
import com.smte.skeererer.feature.playgame.presentation.components.OverlayComponent
import com.smte.skeererer.feature.playgame.presentation.components.TitleComponent

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    MenuBackgroundColumn(contentModifier = Modifier.padding(24.dp)) {
        BackIconButton(onClick = onNavigateUp)

        TitleComponent(
            text = stringResource(R.string.settings_title),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OverlayComponent(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.sound_settings_title),
                    fontFamily = gillSansBoldFontFamily,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = state.soundState,
                    onCheckedChange = viewModel::changeSoundState,
                )
            }
        }
    }
}