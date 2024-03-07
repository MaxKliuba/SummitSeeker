package com.smte.skeererer.feature.playgame.presentation.ratings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.core.palameciaTitlingFontFamily
import com.smte.skeererer.feature.playgame.domain.model.GameScore

@Composable
fun ScoreList(
    scores: List<GameScore>,
    modifier: Modifier = Modifier
) {
    val maxNumberLength = scores.size.toString().length

    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = scores,
            key = { _, score -> score.timestamp }
        ) { index, score ->
            val number = index + 1
            Text(
                text = "$number.${" ".repeat(1 + maxNumberLength - number.toString().length)}${score.score}",
                fontFamily = palameciaTitlingFontFamily,
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}