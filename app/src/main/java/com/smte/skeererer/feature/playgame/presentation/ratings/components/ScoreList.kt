package com.smte.skeererer.feature.playgame.presentation.ratings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smte.skeererer.R
import com.smte.skeererer.core.gillSansBoldFontFamily
import com.smte.skeererer.feature.playgame.domain.model.GameScore

@Composable
fun ScoreList(
    scores: List<GameScore>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = scores,
            key = { _, score -> score.timestamp }
        ) { index, score ->
            val number = index + 1

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                when (number) {
                    1 -> Image(
                        painter = painterResource(id = R.drawable.medal_gold),
                        contentDescription = number.toString(),
                        modifier = Modifier.size(48.dp)

                    )

                    2 -> Image(
                        painter = painterResource(id = R.drawable.medal_silver),
                        contentDescription = number.toString(),
                        modifier = Modifier.size(48.dp)
                    )

                    3 -> Image(
                        painter = painterResource(id = R.drawable.medal_bronze),
                        contentDescription = number.toString(),
                        modifier = Modifier.size(48.dp)
                    )

                    else -> Text(
                        text = "$number.",
                        fontFamily = gillSansBoldFontFamily,
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Text(
                    text = score.score.toString(),
                    fontFamily = gillSansBoldFontFamily,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}