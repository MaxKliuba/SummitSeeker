package com.smte.skeererer.feature.playgame.domain.model

class Player(
    x: Int,
    y: Int,
    z: Int,
    width: Int,
    height: Int,
    val speed: Int,
    val jumpHeight: Int,
) : GameObject(x, y, z, width, height)
