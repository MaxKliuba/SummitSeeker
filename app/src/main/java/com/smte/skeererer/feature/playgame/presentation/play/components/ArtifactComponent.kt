package com.smte.skeererer.feature.playgame.presentation.play.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.domain.model.Artifact
import com.smte.skeererer.feature.playgame.domain.model.ArtifactType

@Composable
fun ArtifactComponent(
    artifact: Artifact,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    Image(
        painter = painterResource(
            id = when (artifact.type) {
                ArtifactType.COIN -> R.drawable.artifact_coin
                ArtifactType.STAR -> R.drawable.artifact_star
                ArtifactType.HEART -> R.drawable.artifact_heart
                ArtifactType.DIAMOND -> R.drawable.artefact_diamond
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(
                width = with(density) { artifact.sizeX.toDp() },
                height = with(density) { artifact.sizeY.toDp() }
            )
            .offset(
                x = with(density) { artifact.x.toDp() },
                y = with(density) { artifact.y.toDp() }
            )
    )
}