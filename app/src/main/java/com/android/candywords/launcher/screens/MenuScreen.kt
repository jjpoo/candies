package com.android.candywords.launcher.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.candywords.R
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar

@Composable
fun MenuScreen(
    state: CandyUiState,
    toolbarIconModifier: Modifier = Modifier,
    enabled: Boolean = true,
    onPlayClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onShopClicked: () -> Unit = {},
    closeApp: () -> Unit = {}
) {
    val shopItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SHOP.name }
    val settingsItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SETTINGS.name }

    val listOfItems = listOf(shopItem, settingsItem)

    BackHandler {
        closeApp()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_menu),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.img_rocket),
            contentScale = ContentScale.Fit,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxSize(),
            contentDescription = null
        )
        GameToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            listOfToolbarItems = listOfItems.requireNoNulls(),
            toolbarIconModifier = toolbarIconModifier,
            onToolbarItemClicked = {
                when (it) {
                    ToolbarIcons.SHOP.name -> onShopClicked()
                    ToolbarIcons.SETTINGS.name -> onSettingsClicked()
                }
            }
        )

        Image(
            painter = painterResource(id = R.drawable.btn_play_hdpi),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 50.dp)
                .width(250.dp)
                .height(75.dp)
                .clickable {
                    onPlayClicked()
                },
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.popup_bear_hdpi),
            modifier = Modifier.align(Alignment.BottomCenter),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        state = CandyUiState(),
        onPlayClicked = {},
        onSettingsClicked = {},
        onShopClicked = {}
    )
}
