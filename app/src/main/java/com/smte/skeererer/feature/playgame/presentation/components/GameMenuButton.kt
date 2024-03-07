package com.smte.skeererer.feature.playgame.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.smte.skeererer.core.palameciaTitlingFontFamily

@Composable
fun GameMenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontFamily = palameciaTitlingFontFamily,
            fontSize = 32.sp,
        )
    }
}