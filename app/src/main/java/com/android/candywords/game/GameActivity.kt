package com.android.candywords.game

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.data.Hint
import com.android.candywords.data.Level
import com.android.candywords.data.SharedPrefsManager
import com.android.candywords.data.SoundOption
import com.android.candywords.game.composables.GameScreen
import com.android.candywords.game.composables.LobbyScreen
import com.android.candywords.game.composables.ShopScreen
import com.android.candywords.main.MainActivity
import com.android.candywords.navigation.GameNavGraph
import com.android.candywords.state.CandyUiState
import music.MediaPlayerService

class GameActivity : ComponentActivity() {

    private var playerService: MediaPlayerService? = null
    private var isBound: Boolean = false
    private val viewModel: GameViewModel by viewModels()
    private lateinit var connectivityObserver: ConnectivityObserver

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MediaPlayerService.MediaPlayerBinder
            playerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MediaPlayerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun startMusic() {
        playerService?.startMusic() ?: Log.e("PLAY MUSIC", "The value is null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isOpenShopScreen = intent.getBooleanExtra("SHOP", false)
        viewModel.updateMoneyState(SharedPrefsManager.getMoney(this))

        setContent {
            val uiState = viewModel.state.collectAsState()
            val navController = rememberNavController()

            val isTapSoundSelected = uiState.value.soundOptionsState.get(1).isSelected

            val startedScreen =
                if (isOpenShopScreen) GameNavGraph.SHOP.route else GameNavGraph.GAME.route

            NavHost(
                navController = navController,
                startDestination = startedScreen
            ) {
                composable(route = GameNavGraph.GAME.route) {
                    GameScreen(
                        candies = listOf(),
                        // Testing, Remove after complete
                        hintCount = 3,
                        uiState = uiState.value,
                        uiEvent = viewModel::handleUiEvent,
                        onShopClicked = {
                            setSoundOnClick(isTapSoundSelected)
                            navController.navigate(GameNavGraph.SHOP.route)
                        },
                        onLobbyClicked = {
                            setSoundOnClick(isTapSoundSelected)
                            navController.navigate(GameNavGraph.LOBBY.route)
                        }
                    )
                }
                composable(route = GameNavGraph.LOBBY.route) {
                    LobbyScreen(
                        uiState = uiState.value,
                        uiEvent = viewModel::handleUiEvent,
                        getLevelsState = {
                            setSoundOnClick(isTapSoundSelected)
                            getLevelState(state = uiState.value)
                        },
                        onHomeClicked = {
                            setSoundOnClick(isTapSoundSelected)
                            saveLevelState(state = uiState.value)
                            finish()
                            // this is start animation from sratch, not that i need
//                            navigateToHomeScreen()
                        }
                    )
                }

                composable(route = GameNavGraph.SHOP.route) {

                    ShopScreen(
                        uiState = uiState.value,
                        uiEvent = viewModel::handleUiEvent,
                        setSoundOnClick = { setSoundOnClick(isTapSoundSelected) },
                        onHomeClicked = {
                            setSoundOnClick(isTapSoundSelected)
                            finish()
                        },
                        onBuyClicked = {
                            setSoundOnClick(isTapSoundSelected)
                            when (it) {
                                Hint.HINTX1 -> {
                                    updateMoneyCount(100)
                                }

                                Hint.HINTX2 -> {
                                    updateMoneyCount(250)
                                }

                                Hint.HINTX3 -> {
                                    updateMoneyCount(400)
                                }
                            }
                            Log.e(
                                "Money count",
                                "${SharedPrefsManager.getMoney(this@GameActivity)}"
                            )
                        }
                    )
                }
            }
        }
    }

    private fun setSoundForWin(isSoundSelected: Boolean) {
        if (isSoundSelected) {
            playerService?.startWinSound()
        } else {
            playerService?.stopWinSound()
        }
    }

    private fun setSoundOnClick(isSoundSelected: Boolean) {
        if (isSoundSelected) {
            playerService?.setClickSound()
        } else {
            playerService?.stopClickSound()
        }
    }

    private fun updateMoneyCount(amountToReduce: Int) {
        val currentBugsCount = SharedPrefsManager.getMoney(this)
        val newBugsCount = currentBugsCount - amountToReduce
        if (newBugsCount <= 0) {
            Log.e("YOU ARE LOH", "YOU dont have a log of money")
        } else {
            SharedPrefsManager.saveMoney(this, newBugsCount)
        }
        viewModel.updateMoneyState(newBugsCount)
    }

    // save to shared prefs when level will change
    private fun saveLevelState(state: CandyUiState) {
        val levels = SharedPrefsManager.saveLevelState(this@GameActivity, state.levelsLobby)
        Log.e("SAVED LEVELS:", "$levels")
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

    private fun getSettings(settings: List<SoundOption>) {
        try {
            val settingsFromLocalStorage = SharedPrefsManager.getSettings(this)
            viewModel.setSettings(settingsFromLocalStorage)
        } catch (e: NullPointerException) {
            val settingsInitialState: List<SoundOption> = settings
            viewModel.setSettings(settingsInitialState)
        }
    }
}