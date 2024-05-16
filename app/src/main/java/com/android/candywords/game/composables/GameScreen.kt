package com.android.candywords.game.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.candywords.R
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar

@Composable
fun GameScreen(
    candies: List<String>,
    money: Int,
    hintCount: Int,
    uiState: CandyUiState,
    onShopClicked: () -> Unit,
    onLobbyClicked: () -> Unit,
    uiEvent: (CandyUiEvent) -> Unit
) {
    val shopItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SHOP.name }
    val settingsItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.LOBBY.name }
    val listOfItems = mutableListOf(shopItem, settingsItem)

    Box(modifier = Modifier.fillMaxSize()) {

        MainBackground(
            hintCountText = hintCount,
            isGameScreen = true,
            uiState = uiState,
            onShopClicked = { onShopClicked() },
            onMenuClicked = { onLobbyClicked() },
            uiEvent = uiEvent,
            gameToolbar = {
                GameToolbar(
                    listOfToolbarItems = listOfItems.requireNoNulls(),
                    toolbarIconModifier = Modifier,
                    isGameScreen = true,
                    money = money,
                    onItemClicked = {
                        when (it) {
                            ToolbarIcons.SHOP.name -> onShopClicked()
                            ToolbarIcons.LOBBY.name -> onLobbyClicked()
                        }
                    }
                )
            }
        ) {
            GameField(
                uiState = uiState,
                uiEvent = uiEvent
            )
        }
//        if (uiState.currentLevel.isCompleted) {
        Image(
            painter = painterResource(id = R.drawable.bg_shadow_win_mdpi),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.popup_bear),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imagesRes =
                listOf(R.drawable.ic_restart_mdpi, R.drawable.ic_play_mdpi, R.drawable.ic_home_mdpi)

            imagesRes.forEach { image ->
                Image(
                    painter = painterResource(id = image),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier,
                    contentDescription = null
                )
            }
        }
//        }
    }
}

@Preview
@Composable
fun GameScreendPreview() {
    GameScreen(
        candies = listOf("Candy", "Sweet", "Sugar"),
        onShopClicked = {},
        onLobbyClicked = {},
        uiState = CandyUiState(),
        uiEvent = {},
        hintCount = 2,
        money = 50
    )
}