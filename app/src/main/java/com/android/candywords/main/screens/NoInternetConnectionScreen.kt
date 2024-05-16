package com.android.candywords.main.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
fun NoInternetConnectionScreen(
    state: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit,
    retry: () -> Unit = {}
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
            uiEvent(CandyUiEvent.ShowNoInternetConnectionPopup(false))
        }
    )
    NoInternetContent(
        onOkClicked = {
            uiEvent(CandyUiEvent.ShowNoInternetConnectionPopup(false))
            retry()
        }
    )
}

@Composable
fun NoInternetContent(
    onOkClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (box, okButton, text) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.popup_something_wrong_mdpi),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(box) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 20.dp),
            contentScale = ContentScale.FillBounds
        )

        OutlinedText(
            text = stringResource(id = R.string.something_went_wrong),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(text) {
                start.linkTo(box.start)
                bottom.linkTo(box.bottom)
                end.linkTo(box.end)
                top.linkTo(box.top)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.btn_ok_hdpi),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(okButton) {
                    bottom.linkTo(box.bottom)
                    top.linkTo(text.bottom)
                }
                .padding(horizontal = 100.dp)
                .padding(top = 50.dp)
                .clickable {
                    onOkClicked()
                },
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun NoInternetConnectionScreenPreview() {
    NoInternetConnectionScreen(
        state = CandyUiState(),
        {}
    )
}