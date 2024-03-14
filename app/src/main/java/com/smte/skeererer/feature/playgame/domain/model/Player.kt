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

    fun hasCollisionWith(other: GameObject): Boolean {
        val minX1 = x + sizeX
        val maxX1 = minX1 + 1
        val minY1 = y
        val maxY1 = y + sizeY

        val minX2 = other.x
        val maxX2 = other.x + other.sizeX
        val minY2 = other.y
        val maxY2 = other.y + other.sizeY

        return !(minX1 > maxX2 || maxX1 < minX2 ||
                minY1 > maxY2 || maxY1 < minY2)
    }
}
