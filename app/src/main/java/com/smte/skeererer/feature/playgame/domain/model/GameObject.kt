package com.smte.skeererer.feature.playgame.domain.model

import java.util.UUID

abstract class GameObject(
    val id: UUID,
    val x: Int,
    val y: Int,
    val sizeX: Int,
    val sizeY: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameObject

        if (id != other.id) return false
        if (x != other.x) return false
        if (y != other.y) return false
        if (sizeX != other.sizeX) return false
        return sizeY == other.sizeY
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + x
        result = 31 * result + y
        result = 31 * result + sizeX
        result = 31 * result + sizeY
        return result
    }
}