package com.smte.skeererer.core

sealed class Screen(val route: String) {
    data object Menu : Screen("menu")
    data object Play : Screen("play")
    data object Settings : Screen("settings")
    data object Ratings : Screen("ratings")
}