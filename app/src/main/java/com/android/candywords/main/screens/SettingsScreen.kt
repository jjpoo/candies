package com.android.candywords.main.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar
import com.android.candywords.utils.OutlinedText

@Composable
fun SettingsScreen(
    state: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit,
    saveSettings: () -> Unit,
    onCrossClicked: () -> Unit
) {
    val crossItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.CROSS.name }
    val shopItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SHOP.name }

    val listOfItems = listOf(shopItem, crossItem).requireNoNulls()

    Image(
        painter = painterResource(id = R.drawable.bg_shadow_hdpi),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
    GameToolbar(
        listOfToolbarItems = listOfItems,
        firstItemAlpha = 0f,
        isGameScreen = false,
        onItemClicked = {
            Log.e("STRING", it)
            if (it == ToolbarIcons.CROSS.name) {
                uiEvent(CandyUiEvent.OnSettingsClicked)
            }
        }
    )
    SettingsContent(
        state = state,
        onSoundSelected = {
            Log.e("NAME", "${it}")
            uiEvent(CandyUiEvent.OnSettingsSelected(it))
        },
        onSaveClicked = {
            saveSettings()
            uiEvent(CandyUiEvent.OnSettingsClicked)
        }
    )
}

@Composable
fun SettingsContent(
    state: CandyUiState,
    onSoundSelected: (Int) -> Unit = {},
    onSaveClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (box, settingsTitle, saveTitle, content) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.popup_settings_hdpi),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(box) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(350.dp)
                .padding(horizontal = 20.dp)
        )
        SettingsItems(
            state = state,
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(box.start)
                    top.linkTo(box.top)
                    end.linkTo(box.end)
                    bottom.linkTo(box.bottom)
                }
                .padding(horizontal = 50.dp),
            onSoundSelected = onSoundSelected
        )

        Image(
            painter = painterResource(id = R.drawable.title_settings_hdpi),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(settingsTitle) {
                    start.linkTo(box.start)
                    top.linkTo(box.top)
                    end.linkTo(box.end)
                }
                .padding(horizontal = 40.dp),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.btn_save_hdpi),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(saveTitle) {
                    start.linkTo(box.start)
                    bottom.linkTo(box.bottom)
                    end.linkTo(box.end)
                }
                .padding(horizontal = 100.dp)
                .padding(top = 50.dp)
                .clickable {
                    onSaveClicked()
                },
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun SettingsItems(
    modifier: Modifier = Modifier,
    state: CandyUiState,
    onSoundSelected: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        state.soundOptionsState.forEachIndexed { index, item ->
            SettingsItemRow(
                text = item.name,
                isToggled = item.isSelected,
                onClick = {
                    onSoundSelected(index)
                }
            )
        }
    }
}

@Composable
fun SettingsItemRow(
    text: String,
    isToggled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedText(text = text)
        Image(
            painter = painterResource(
                id = if (!isToggled) {
                    R.drawable.switch_off_hdpi
                } else {
                    R.drawable.switch_on_hdpi
                }
            ),
            modifier = Modifier
                .width(80.dp)
                .height(30.dp),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(state = CandyUiState(), {}, {}, {})
}

@Preview
@Composable
fun Set() {
    SettingsItems(state = CandyUiState())
}