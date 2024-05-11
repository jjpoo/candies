package com.android.candywords.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.candywords.MainViewModel
import com.android.candywords.MenuScreen
import com.android.candywords.R
import com.android.candywords.navigation.NavGraph
import com.android.candywords.ui.theme.CandyWordsTheme

class LauncherActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState = viewModel.state.collectAsState()

            val navController = rememberNavController()
            CandyWordsTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavGraph.Launcher.route
                ) {
                    composable(
                        route = NavGraph.Launcher.route,
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        }
                    ) {
                        GameLauncherScreen(
                            text = stringResource(id = R.string.app_name),
                            launcherBacImageRes = R.drawable.loading_fill,
                            expanded = true,
                            navigateToMainScreen = {
                                navController.navigate(NavGraph.Menu.route)
                            }
                        )
                    }
                    composable(
                        route = NavGraph.Menu.route,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessVeryLow
                                )
                            )
                        }
                    ) {
                        MenuScreen(
                            state = uiState.value
                        )
                    }
                }
            }
        }
    }
}