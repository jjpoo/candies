package com.android.candywords.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.utils.OutlinedText
import com.android.candywords.utils.WordsBlock

@Composable
fun MainBackground(
    money: Int = 50,
    hintCountText: Int = 0,
    isGameScreen: Boolean = true,
    uiEvent: (CandyUiEvent) -> Unit,
    onShopClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    gameToolbar: @Composable (Int) -> Unit,
    candies: List<String> = listOf(),
    gameField: @Composable (Modifier) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_menu),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Column {
            gameToolbar(money)

            if (isGameScreen) {
                WordsBlock(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    candies = candies
                )
            }
        }

        gameField(
            Modifier.align(Alignment.Center)
        )

        BottomBox(
            modifier = Modifier.align(Alignment.BottomCenter),
            hintCountText = hintCountText,
            // Just for testing
            onHintClicked = {
                uiEvent(CandyUiEvent.OpenLevel(it))
            }
        )
    }
}

@Composable
fun BottomBox(
    modifier: Modifier = Modifier,
    hintCountText: Int,
    onHintClicked: (Int) -> Unit
) {

    Box(
        modifier = modifier.height(height = 255.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.popup_bear),
            contentDescription = null,
            Modifier
                .fillMaxSize()
                .offset { IntOffset(0, 185) },
        )

        ConstraintLayout(
            modifier = Modifier
                .clickable {
                    onHintClicked(1)
                }
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            val (hintIcon, hintCount, hintText) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_hints_hdpi),
                modifier = Modifier
                    .constrainAs(hintIcon) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(80.dp),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.ic_hint_count_hdpi),
                modifier = Modifier
                    .constrainAs(hintCount) {
                        end.linkTo(hintIcon.end)
                        bottom.linkTo(hintIcon.bottom)
                    }
                    .size(40.dp),
                contentDescription = null
            )
            OutlinedText(
                text = stringResource(id = R.string.x, hintCountText),
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(hintText) {
                    end.linkTo(hintCount.end)
                    bottom.linkTo(hintCount.bottom)
                    top.linkTo(hintCount.top)
                    start.linkTo(hintCount.start)
                }
            )
        }
    }
}

@Preview
@Composable
fun MainBackgroundPreview() {
    MainBackground(
        candies = listOf("Candy", "Sweet", "Sugar"),
        hintCountText = 2,
        onShopClicked = {},
        onMenuClicked = {},
        gameToolbar = {},
        uiEvent = {},
        gameField = {
            GameField(modifier = it, uiState = CandyUiState(), uiEvent = {})
        }
    )
}