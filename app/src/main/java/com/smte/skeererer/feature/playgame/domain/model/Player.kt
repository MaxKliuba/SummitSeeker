package com.smte.skeererer.feature.playgame.domain.model

class Player(
    x: Int,
    y: Int,
    sizeX: Int,
    sizeY: Int,
) : GameObject(x, y, sizeX, sizeY) {

    fun copy(
        x: Int = this.x,
        y: Int = this.y,
        sizeX: Int = this.sizeX,
        sizeY: Int = this.sizeY,
    ): Player = Player(x, y, sizeX, sizeY)
}
