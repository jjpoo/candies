package com.android.candywords.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.candywords.MainViewModel
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.data.Level
import com.android.candywords.data.SharedPrefsManager
import com.android.candywords.main.MainActivity
import com.android.candywords.navigation.GameNavGraph
import com.android.candywords.state.CandyUiState

class GameActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState = viewModel.state.collectAsState()
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = GameNavGraph.GAME.route
            ) {
                composable(route = GameNavGraph.GAME.route) {
                    GameScreen(
                        candies = listOf(),
                        // Testing, Remove after complete
                        money = 50,
                        hintCount = 3,
                        uiState = uiState.value,
                        uiEvent = viewModel::handleUiEvent,
                        onShopClicked = { /*TODO*/ },
                        onLobbyClicked = {
                            navController.navigate(GameNavGraph.LOBBY.route)
                        }
                    )
                }
                composable(route = GameNavGraph.LOBBY.route) {
                    LobbyScreen(
                        uiState = uiState.value,
                        uiEvent = viewModel::handleUiEvent,
                        getLevelsState = {
                            getLevelState(state = uiState.value)
                        },
                        onHomeClicked = {
                            saveLevelState(state = uiState.value)
                            finish()
                            // this is start animation from sratch, not that i need
//                            navigateToHomeScreen()
                        }
                    )
                }
            }
            // Test purpose
//            GameField(
//                uiState = uiState.value,
//                uiEvent = viewModel::handleUiEvent
//            )
        }
    }

    // save to shared prefs when level will change
    private fun saveLevelState(state: CandyUiState) {
//        if (state.isLevelPassed) {
            val levels = SharedPrefsManager.saveLevelState(this@GameActivity, state.levelsLobby)
            Log.e("SAVED LEVELS:", "$levels")
//        } else {
//            SharedPrefsManager.saveLevelState(this@GameActivity, state.levels)
//        }
    }

    private fun getLevelState(state: CandyUiState): List<Level> {
        return try {
            SharedPrefsManager.getLevelState(this)
        } catch (e: NullPointerException) {
            state.levelsLobby
        }
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(this@GameActivity, MainActivity::class.java))
    }
}