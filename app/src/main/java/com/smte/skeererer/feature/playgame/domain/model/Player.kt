package com.smte.skeererer.feature.playgame.domain.model

class Player(
    x: Int,
    y: Int,
    sizeX: Int,
    sizeY: Int,
    val state: PlayerState,
) : GameObject(x, y, sizeX, sizeY) {

    fun copy(
        x: Int = this.x,
        y: Int = this.y,
        sizeX: Int = this.sizeX,
        sizeY: Int = this.sizeY,
        state: PlayerState = this.state,
    ): Player = Player(x, y, sizeX, sizeY, state)

    fun hasCollisionWith(other: GameObject): Boolean {
        val minX1 = x + sizeX / 2
        val maxX1 = x + sizeX
        val minY1 = y
        val maxY1 = y + sizeY

        val minX2 = other.x
        val maxX2 = other.x + other.sizeX
        val minY2 = other.y
        val maxY2 = other.y + other.sizeY

        return !(minX1 > maxX2 || maxX1 < minX2 ||
                minY1 > maxY2 || maxY1 < minY2)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Player

        return state == other.state
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + state.hashCode()
        return result
    }
}
