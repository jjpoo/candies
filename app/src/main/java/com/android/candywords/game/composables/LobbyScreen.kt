package com.android.candywords.game.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.candywords.R
import com.android.candywords.data.Level
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar

@Composable
fun LobbyScreen(
    uiState: CandyUiState,
    getLevelsState: () -> List<Level>,
    uiEvent: (CandyUiEvent) -> Unit,
    onHomeClicked: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        val updatedLevels = getLevelsState()
        Log.e("SAVED LEVELS FROM UI:", "$updatedLevels")

        uiEvent(CandyUiEvent.UpdateLevelsState(updatedLevels))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MainBackground(
            isGameScreen = false,
            onShopClicked = { /*TODO*/ },
            onMenuClicked = { /*TODO*/ },
            uiEvent = uiEvent,
            uiState = uiState,
            gameToolbar = {
                LobbyToolbar(
                    onHomeClicked = onHomeClicked
                )
            }
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                columns = GridCells.Fixed(3)
            ) {
                items(uiState.levelsLobby) { image ->
                    val levelImage =
                        if (image.isOpened) image.imageRes
                        else if (image.isComingSoon) R.drawable.ic_level_coming_soon_hdpi
                        else R.drawable.ic_level_locked_hdpi
                    LevelItem(painterResource(id = levelImage))
                }
            }
        }
    }
}

@Composable
fun LobbyToolbar(
    onHomeClicked: () -> Unit
) {
    val homeIcon1 = ToolbarIcons.entries.find { it.name == ToolbarIcons.HOME.name }
    val homeIcon2 = ToolbarIcons.entries.find { it.name == ToolbarIcons.HOME.name }

    val listOfItems = listOf(homeIcon1, homeIcon2)

    GameToolbar(
        firstItemAlpha = 0f,
        listOfToolbarItems = listOfItems.requireNoNulls(),
        toolbarIconModifier = Modifier,
        isGameScreen = true,
        onItemClicked = {
            when (it) {
                ToolbarIcons.HOME.name -> onHomeClicked()
            }
        }
    )
}

@Composable
fun LevelItem(
    levelImage: Painter
) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = levelImage,
            contentDescription = null
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun LobbyScreenPreview() {
    LobbyScreen(
        uiState = CandyUiState(),
        onHomeClicked = {},
        uiEvent = {},
        getLevelsState = { listOf() }
    )
}