package com.smte.skeererer.feature.playgame.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.core.gillSansBoldFontFamily

@Composable
fun TitleComponent(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.uppercase(),
        fontFamily = gillSansBoldFontFamily,
        fontSize = 40.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.padding(start = 8.dp, top = 40.dp, end = 8.dp, bottom = 8.dp)
    )
}