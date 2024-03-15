package com.smte.skeererer.feature.playgame.domain.model

class GameBackground(
    offset1: Int,
    offset2: Int,
    sizeX: Int,
    sizeY: Int,
) : GameObject(offset1, offset2, sizeX, sizeY) {

    fun copy(
        x: Int = this.x,
        y: Int = this.y,
        sizeX: Int = this.sizeX,
        sizeY: Int = this.sizeY,
    ): GameBackground = GameBackground(x, y, sizeX, sizeY)
}