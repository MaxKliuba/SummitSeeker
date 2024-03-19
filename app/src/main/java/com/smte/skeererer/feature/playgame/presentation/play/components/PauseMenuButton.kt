package com.smte.skeererer.feature.playgame.presentation.play.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.R
import com.smte.skeererer.core.gillSansBoldFontFamily

@Composable
fun PauseMenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = R.drawable.button_background),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(95.dp)
        )

        Text(
            text = text.uppercase(),
            fontFamily = gillSansBoldFontFamily,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}