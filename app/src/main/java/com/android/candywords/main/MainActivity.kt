package com.android.candywords.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.candywords.MainViewModel
import com.android.candywords.ShopActivity
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.connectivity.ConnectivityObserverImpl
import com.android.candywords.data.SettingOption
import com.android.candywords.data.SharedPrefsManager
import com.android.candywords.game.GameActivity
import com.android.candywords.main.screens.MainScreen
import com.android.candywords.navigation.MainNavGraph
import com.android.candywords.ui.theme.CandyWordsTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSettings(settings = viewModel.state.value.settings)

        connectivityObserver = ConnectivityObserverImpl(applicationContext)
        observeConnectivityStatus(connectivityObserver)
        getInitialConnectivityStatus(connectivityObserver = connectivityObserver)

        setContent {
            val uiState = viewModel.state.collectAsState()
            val navController = rememberNavController()

            CandyWordsTheme {
                NavHost(
                    navController = navController,
                    startDestination = MainNavGraph.Launcher.route
                ) {
                    composable(
                        route = MainNavGraph.Launcher.route,
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        }
                    ) {
                        MainScreen(
                            state = uiState.value,
                            uiEvent = viewModel::handleUiEvent,
                            onPlayClicked = {
                                navigateToGameScreen()
                            },
                            onShopClicked = {
                                navigateToShopScreen()
                            },
                            closeApp = {
                                finish()
                            },
                            saveSettings = {
                                saveSettings(uiState.value.settings)
                            },
                            retry = {
                                this@MainActivity.recreate()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getInitialConnectivityStatus(connectivityObserver: ConnectivityObserver) {
        val initialStatus = connectivityObserver.getCurrentNetworkState()
        viewModel.updateConnectivityStatus(
            status = initialStatus
        )
        Log.e("CONNECTIVTY INITIAL STATUS", "${viewModel.state.value.connectivityStatus}")
    }

    private fun observeConnectivityStatus(connectivityObserver: ConnectivityObserver) {
        connectivityObserver.observe().onEach {
            viewModel.updateConnectivityStatus(status = it)
        }.launchIn(lifecycleScope)
    }

    private fun getSettings(settings: List<SettingOption>) {
        try {
            val settingsFromLocalStorage = SharedPrefsManager.getSettings(this)
            viewModel.setSettings(settingsFromLocalStorage)
        } catch (e: NullPointerException) {
            val settingsInitialState: List<SettingOption> = settings
            viewModel.setSettings(settingsInitialState)
        }
    }

    private fun saveSettings(
        selectedSettings: List<SettingOption>
    ) {
        SharedPrefsManager.saveSettings(this, selectedSettings)
        Log.e("SAVED SETTINGS from ui", "$selectedSettings")
    }

    private fun navigateToGameScreen() {
        startActivity(Intent(this@MainActivity, GameActivity::class.java))
    }

    private fun navigateToShopScreen() {
        startActivity(Intent(this@MainActivity, ShopActivity::class.java))
    }
}