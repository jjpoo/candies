package com.android.candywords.main.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.android.candywords.R
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.launcher.ConstraintLoadingImage
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar

@Composable
fun MainScreen(
    state: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit = {},
    onPlayClicked: () -> Unit = {},
    onShopClicked: () -> Unit = {},
    closeApp: () -> Unit = {},
    saveSettings: () -> Unit = {},
    retry: () -> Unit = {}
) {

    if (state.connectivityStatus != ConnectivityObserver.Status.Available) {
        uiEvent(CandyUiEvent.ShowNoInternetConnectionPopup(true))
    }

    BackHandler {
        closeApp()
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val shopItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SHOP.name }
    val settingsItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SETTINGS.name }

    val listOfItems = mutableListOf(shopItem, settingsItem)

    LaunchedEffect(key1 = Unit) {
        expanded = !expanded
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_menu),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        GameToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            listOfToolbarItems = listOfItems.requireNoNulls(),
            toolbarIconModifier = Modifier,
            isGameScreen = false,
            onItemClicked = {

                when (it) {
                    ToolbarIcons.SHOP.name -> onShopClicked()
                    ToolbarIcons.SETTINGS.name -> {
                        uiEvent(CandyUiEvent.OnSettingsClicked)
                    }
                }
            }
        )

        Image(
            painter = painterResource(id = R.drawable.img_rocket),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )

        Image(
            painter = painterResource(id = R.drawable.btn_play_hdpi),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 50.dp)
                .padding(top = 400.dp)
                .width(250.dp)
                .height(75.dp)
                .clickable { onPlayClicked() }
        )

        ConstraintLoadingImage(
            expanded = expanded,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .offset { IntOffset(0, 350) }
        )

        if (state.isSettingsShown) {
            uiEvent(CandyUiEvent.UpdateToolbarButtonState(ToolbarIcons.SETTINGS.name))
            SettingsScreen(
                state = state,
                uiEvent = uiEvent,
                saveSettings = {
                    saveSettings()
                },
                onCrossClicked = {
                    uiEvent(CandyUiEvent.OnSettingsClicked)
                }
            )
        }

        if (state.isNoInternetPopupShown) {
            NoInternetConnectionScreen(
                state = state,
                uiEvent = uiEvent,
                retry = retry
            )
        }
    }
}

@Preview
@Composable
fun GameLauncherScreenPreview() {
    MainScreen(
        state = CandyUiState(isSettingsShown = false)
    )
}

