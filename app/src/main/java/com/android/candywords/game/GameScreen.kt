package com.android.candywords.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    MainBackground(
        hintCountText = hintCount,
        candies = candies,
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