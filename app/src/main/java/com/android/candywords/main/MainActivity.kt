package com.android.candywords.main

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
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.connectivity.ConnectivityObserverImpl
import com.android.candywords.data.SharedPrefsManager
import com.android.candywords.data.SoundOption
import com.android.candywords.game.GameActivity
import com.android.candywords.launcher.LauncherScreen
import com.android.candywords.main.screens.MainScreen
import com.android.candywords.music.MediaPlayerService
import com.android.candywords.navigation.MainNavGraph
import com.android.candywords.ui.theme.CandyWordsTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var connectivityObserver: ConnectivityObserver
    private var playerService: MediaPlayerService? = null
    private var isBound: Boolean = false

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

    private fun setUpSound(soundOptions: List<SoundOption>) {
        soundOptions.forEach { soundOption ->
            if (soundOption.id == 0) {
                if (soundOption.isSelected) {
                    playerService?.startMusic() ?: Log.e("PLAY MUSIC", "The value is null")
                } else {
                    playerService?.stopMusic()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInitialMoneyValue()

        getSettings(settings = viewModel.state.value.soundOptionsState)

        /** Handle Connectivity Status **/
        connectivityObserver = ConnectivityObserverImpl(applicationContext)
        observeConnectivityStatus(connectivityObserver)
        getInitialConnectivityStatus(connectivityObserver = connectivityObserver)
        /** Handle Connectivity Status **/

        setContent {
            val uiState = viewModel.state.collectAsState()
            val navController = rememberNavController()

            val isTapSoundSelected = uiState.value.soundOptionsState.get(1).isSelected
            setUpSound(uiState.value.soundOptionsState)

            val isAnimated = intent.getBooleanExtra(GameActivity.IS_ANIMATED, true)

            CandyWordsTheme {
                NavHost(
                    navController = navController,
                    startDestination = if (isAnimated) MainNavGraph.Launcher.route else MainNavGraph.Menu.route
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
                        LauncherScreen(
                            state = uiState.value,
                            uiEvent = viewModel::handleUiEvent,
                            onPlayClicked = {
                                setSoundOnClick(isTapSoundSelected)
                                navigateToGameScreen()
                            },
                            onShopClicked = {
                                setSoundOnClick(isTapSoundSelected)
                                navigateToShopScreen()
                            },
                            closeApp = {
                                setSoundOnClick(isTapSoundSelected)
                                finish()
                            },
                            saveSettings = {
                                saveSettings(uiState.value.soundOptionsState)
                            },
                            retry = {
                                setSoundOnClick(isTapSoundSelected)
                                this@MainActivity.recreate()
                            }
                        )
                    }

                    composable(MainNavGraph.Menu.route) {
                        MainScreen(
                            state = uiState.value,
                            uiEvent = viewModel::handleUiEvent,
                            onPlayClicked = {
                                setSoundOnClick(isTapSoundSelected)
                                navigateToGameScreen()
                            },
                            onShopClicked = {
                                setSoundOnClick(isTapSoundSelected)
                                navigateToShopScreen()
                            },
                            closeApp = {
                                setSoundOnClick(isTapSoundSelected)
                                finish()
                            },
                            saveSettings = {
                                saveSettings(uiState.value.soundOptionsState)
                            },
                            retry = {
                                setSoundOnClick(isTapSoundSelected)
                                this@MainActivity.recreate()
                            }
                        )
                    }
                }
            }
        }
    }


    private fun setSoundOnClick(isSoundSelected: Boolean) {
        if (isSoundSelected) {
            playerService?.setClickSound()
        } else {
            playerService?.stopClickSound()
        }
    }

    // when win
    private fun setSoundVibration(isSoundSelected: Boolean) {
        if (isSoundSelected) {
            playerService?.startVibration()
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

    private fun getSettings(settings: List<SoundOption>) {
        try {
            val settingsFromLocalStorage = SharedPrefsManager.getSettings(this)
            viewModel.setSettings(settingsFromLocalStorage)
        } catch (e: NullPointerException) {
            val settingsInitialState: List<SoundOption> = settings
            viewModel.setSettings(settingsInitialState)
        }
    }

    private fun saveSettings(
        selectedSettings: List<SoundOption>
    ) {
        SharedPrefsManager.saveSettings(this, selectedSettings)
        Log.e("SAVED SETTINGS from ui", "$selectedSettings")
    }

    private fun navigateToGameScreen() {
        startActivity(Intent(this@MainActivity, GameActivity::class.java))
    }

    private fun navigateToShopScreen() {
        startActivity(
            Intent(this@MainActivity, GameActivity::class.java).putExtra("SHOP", true)
        )
    }

    private fun setInitialMoneyValue() {
        if (SharedPrefsManager.getMoney(this) == 0) {
            SharedPrefsManager.saveMoney(
                this,
                50
            )
            viewModel.updateMoneyState(50)
        }
    }
}