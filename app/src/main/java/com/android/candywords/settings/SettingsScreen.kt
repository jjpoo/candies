package com.android.candywords.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.android.candywords.launcher.screens.MenuScreen
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar
import com.android.candywords.utils.OutlinedText

@Composable
fun SettingsScreen(
    state: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit
) {
    val crossItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.CROSS.name }

    val listOfItems = listOf(crossItem).requireNoNulls()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MenuScreen(
            state = state,
            toolbarIconModifier = Modifier
        )
        Image(
            painter = painterResource(id = R.drawable.bg_shadow_hdpi),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        GameToolbar(
            listOfToolbarItems = listOfItems
        )
        SettingsContent(
            state = state,
        )
    }
}

@Composable
fun SettingsContent(
    state: CandyUiState,
    onSettingItemClicked: (Int) -> Unit = {}
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
                .height(230.dp)
                .width(280.dp)
        )
        SettingsItems(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(box.start)
                    top.linkTo(box.top)
                    end.linkTo(box.end)
                    bottom.linkTo(box.bottom)
                }
                .padding(horizontal = 40.dp),
            state = state
        )

        Image(
            painter = painterResource(id = R.drawable.title_settings_hdpi),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(settingsTitle) {
                    start.linkTo(box.start)
                    top.linkTo(box.top)
                    end.linkTo(box.end)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.btn_save_hdpi),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(saveTitle) {
                    start.linkTo(box.start)
                    bottom.linkTo(box.bottom)
                    end.linkTo(box.end)
                }
        )
    }
}

@Composable
fun SettingsItems(
    modifier: Modifier = Modifier,
    state: CandyUiState,
    onSettingItemClicked: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        state.settings.forEachIndexed { index, item ->
            SettingsItemRow(
                text = item.name,
                isToggled = item.isSelected,
                onClick = {
                    onSettingItemClicked(index)
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
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedText(text = text)
        Image(
            painter = painterResource(
                id = if (isToggled) {
                    R.drawable.switch_off_hdpi
                } else {
                    R.drawable.switch_on_hdpi
                }
            ),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(state = CandyUiState(), {})
}

@Preview
@Composable
fun Set() {
    SettingsItems(state = CandyUiState())
}