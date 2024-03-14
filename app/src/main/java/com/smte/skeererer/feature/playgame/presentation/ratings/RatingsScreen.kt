package com.smte.skeererer.feature.playgame.presentation.ratings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.presentation.components.MenuBackgroundColumn
import com.smte.skeererer.feature.playgame.presentation.components.BackIconButton
import com.smte.skeererer.feature.playgame.presentation.components.OverlayComponent
import com.smte.skeererer.feature.playgame.presentation.components.TitleComponent
import com.smte.skeererer.feature.playgame.presentation.ratings.components.ScoreList

@Composable
fun RatingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: RatingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState

    MenuBackgroundColumn(contentModifier = Modifier.padding(24.dp)) {
        BackIconButton(onClick = onNavigateUp)

        TitleComponent(
            text = stringResource(R.string.rating_title),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OverlayComponent(modifier = Modifier.fillMaxWidth()) {
            ScoreList(
                scores = state.scores,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}