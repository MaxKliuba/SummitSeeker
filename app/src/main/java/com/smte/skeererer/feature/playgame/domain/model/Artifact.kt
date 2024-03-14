package com.smte.skeererer.feature.playgame.domain.model

class Artifact(
    x: Int,
    y: Int,
    sizeX: Int,
    sizeY: Int,
    val type: ArtifactType,
) : GameObject(x, y, sizeX, sizeY) {

    fun copy(
        x: Int = this.x,
        y: Int = this.y,
        sizeX: Int = this.sizeX,
        sizeY: Int = this.sizeY,
    ): Artifact = Artifact(x, y, sizeX, sizeY, this.type)
}