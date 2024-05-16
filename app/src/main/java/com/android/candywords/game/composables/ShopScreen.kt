package com.android.candywords.game.composables

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.data.Hint
import com.android.candywords.data.Price
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.GameToolbar
import com.android.candywords.utils.OutlinedText

@Composable
fun ShopScreen(
    uiState: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit,
    setSoundOnClick: () -> Unit,
    onHomeClicked: () -> Unit,
    onBuyClicked: (Hint) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        MainBackground(
            isGameScreen = false,
            uiState = uiState,
            uiEvent = uiEvent,
            isShopScreen = true,
            gameToolbar = {
                ShopToolbar(
                    money = uiState.money,
                    onHomeClicked = onHomeClicked,
                    setSoundOnClick = setSoundOnClick
                )
            }
        ) {
            ShopContent(
                modifier = Modifier.align(Alignment.Center),
                onBuyClicked = onBuyClicked,
                setSoundOnClick = setSoundOnClick
            )
        }
        if (uiState.isMoneyEqualsOrLessZero) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.block_words_mdpi),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                OutlinedText(
                    text = stringResource(id = R.string.you_dont_have_enough_couns),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ShopContent(
    modifier: Modifier,
    setSoundOnClick: () -> Unit,
    onBuyClicked: (Hint) -> Unit
) {

    Box {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (box, title, content) = createRefs()

            Image(
                modifier = Modifier
                    .height(500.dp)
                    .constrainAs(box) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.popup_shop_mdpi),
                contentDescription = null
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        start.linkTo(box.start)
                        top.linkTo(box.top)
                        end.linkTo(box.end)
                        bottom.linkTo(box.bottom)
                    }
                    .padding(bottom = 500.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.title_shop_mdpi),
                contentDescription = null
            )

            HintContent(
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(box.start)
                    top.linkTo(box.top)
                    bottom.linkTo(box.bottom)
                    end.linkTo(box.end)
                }.padding(top = 10.dp),
                onBuyClicked = onBuyClicked,
                setSoundOnClick = setSoundOnClick
            )
        }
    }
}


@Composable
fun HintContent(
    modifier: Modifier,
    onBuyClicked: (Hint) -> Unit,
    setSoundOnClick: () -> Unit,
    listOfPrices: List<Price> = listOf(
        Price(priceValue = 100, imgRes = R.drawable.ic_shop_hint_x1_mdpi, hint = Hint.HINTX1),
        Price(priceValue = 250, imgRes = R.drawable.ic_shop_hint_x3_mdpi, hint = Hint.HINTX2),
        Price(priceValue = 400, imgRes = R.drawable.ic_shop_hint_x5_mdpi, hint = Hint.HINTX3)
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 70.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        listOfPrices.forEach { price ->
            Box(
                modifier = Modifier.clickable {
                    setSoundOnClick()
                    onBuyClicked(price.hint)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.field_shop_list_mdpi),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                HintComposable(
                    modifier = Modifier,
                    hintImageResourse = price.imgRes,
                    moneyCount = price.priceValue
                )
            }
        }
    }
}

@Composable
fun HintComposable(
    modifier: Modifier,
    hintImageResourse: Int,
    moneyCount: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = hintImageResourse),
            modifier = Modifier.size(130.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        MoneyContentHintComposable(
            moneyCount = moneyCount,
            modifier = Modifier
        )
    }
}


@Composable
fun MoneyContentHintComposable(
    moneyCount: Int,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 30.dp).padding(end = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_coin_mdpi),
                modifier = Modifier.size(60.dp),
                contentDescription = null
            )
            OutlinedText(
                text = stringResource(id = R.string.x, moneyCount),
                fontSize = 20.sp,
                modifier = Modifier
            )
        }

        Image(
            painter = painterResource(id = R.drawable.btn_buy_mdpi),
            modifier = Modifier
                .height(42.dp)
                .width(130.dp),
            contentDescription = null
        )
    }
}

@Composable
fun ShopToolbar(
    money: Int = 0,
    onHomeClicked: () -> Unit,
    setSoundOnClick: () -> Unit
) {
    val homeIcon1 = ToolbarIcons.entries.find { it.name == ToolbarIcons.HOME.name }
    val homeIcon2 = ToolbarIcons.entries.find { it.name == ToolbarIcons.HOME.name }

    val listOfItems = listOf(homeIcon1, homeIcon2)

    GameToolbar(
        money = money,
        firstItemAlpha = 0f,
        listOfToolbarItems = listOfItems.requireNoNulls(),
        toolbarIconModifier = Modifier,
        isGameScreen = true,
        onItemClicked = {
            setSoundOnClick()
            when (it) {
                ToolbarIcons.HOME.name -> onHomeClicked()
            }
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ShopScreenPreview() {
    ShopScreen(
        uiState = CandyUiState(
            money = 0
        ),
        onHomeClicked = {},
        uiEvent = {},
        onBuyClicked = {},
        setSoundOnClick = {}
    )
}