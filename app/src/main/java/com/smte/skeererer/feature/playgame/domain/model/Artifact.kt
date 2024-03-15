package com.smte.skeererer.feature.playgame.domain.model

import java.util.UUID

class Artifact(
    id: UUID,
    x: Int,
    y: Int,
    sizeX: Int,
    sizeY: Int,
    val type: ArtifactType,
) : GameObject(id, x, y, sizeX, sizeY) {

    fun copy(
        x: Int = this.x,
        y: Int = this.y,
        sizeX: Int = this.sizeX,
        sizeY: Int = this.sizeY,
    ): Artifact = Artifact(this.id, x, y, sizeX, sizeY, this.type)
}