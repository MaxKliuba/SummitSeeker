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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Artifact

        return type == other.type
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}