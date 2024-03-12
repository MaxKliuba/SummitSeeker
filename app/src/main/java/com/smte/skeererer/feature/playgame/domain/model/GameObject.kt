package com.smte.skeererer.feature.playgame.domain.model

abstract class GameObject(
    val x: Int,
    val y: Int,
    val sizeX: Int,
    val sizeY: Int,
) {
    fun hasCollisionWith(other: GameObject): Boolean {
        val minX1 = x
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

        other as GameObject

        if (x != other.x) return false
        if (y != other.y) return false
        if (sizeX != other.sizeX) return false
        return sizeY == other.sizeY
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + sizeX
        result = 31 * result + sizeY
        return result
    }
}