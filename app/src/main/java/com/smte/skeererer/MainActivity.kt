package com.smte.skeererer

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smte.skeererer.core.Screen
import com.smte.skeererer.feature.playgame.presentation.menu.MenuScreen
import com.smte.skeererer.feature.playgame.presentation.play.PlayScreen
import com.smte.skeererer.feature.playgame.presentation.ratings.RatingsScreen
import com.smte.skeererer.feature.playgame.presentation.settings.SettingsScreen
import com.smte.skeererer.ui.theme.SummitSeekerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeScreenSystemUiController(true)

        setContent {
            SummitSeekerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Menu.route,
                    ) {
                        composable(route = Screen.Menu.route) {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            MenuScreen(
                                onNavigateToPlay = { navController.navigate(Screen.Play.route) },
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                                onNavigateToRatings = { navController.navigate(Screen.Ratings.route) },
                                onQuit = ::finish
                            )
                        }

                        composable(route = Screen.Play.route) {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            PlayScreen(
                                onNavigateUp = navController::navigateUp,
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                            )
                        }

                        composable(route = Screen.Settings.route) {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            SettingsScreen(
                                onNavigateUp = navController::navigateUp
                            )
                        }

                        composable(route = Screen.Ratings.route) {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            RatingsScreen(
                                onNavigateUp = navController::navigateUp
                            )
                        }
                    }
                }
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun changeScreenSystemUiController(isFullScreen: Boolean) {
        window?.also {
            WindowCompat.setDecorFitsSystemWindows(it, !isFullScreen)
            WindowCompat.getInsetsController(it, it.decorView).apply {
                systemBarsBehavior = if (isFullScreen) {
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                } else {
                    WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                }

                if (isFullScreen) {
                    hide(WindowInsetsCompat.Type.systemBars())
                } else {
                    show(WindowInsetsCompat.Type.systemBars())
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.attributes.layoutInDisplayCutoutMode = if (isFullScreen) {
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                } else {
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                }
            }
        }
    }
}