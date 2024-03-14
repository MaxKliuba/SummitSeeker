package com.smte.skeererer.feature.playgame.presentation.play.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.domain.model.Player

@Composable
fun PlayerComponent(
    player: Player,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val animatedPlayerY by animateIntAsState(
        targetValue = player.y,
        label = "playerY",
        animationSpec = spring(stiffness = Spring.StiffnessLow),
    )

    Image(
        painter = painterResource(id = R.drawable.player_run),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(
                width = with(density) { player.sizeX.toDp() },
                height = with(density) { player.sizeY.toDp() }
            )
            .offset(
                x = with(density) { player.x.toDp() },
                y = with(density) { animatedPlayerY.toDp() },
            )
    )
}