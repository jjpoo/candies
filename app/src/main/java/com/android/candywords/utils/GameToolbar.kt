package com.android.candywords.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.state.ToolbarIcons

@Composable
fun GameToolbar(
    firstItemAlpha: Float = 1f,
    secondItemAlpha: Float = 1f,
    money: Int = 50,
    isGameScreen: Boolean,
    listOfToolbarItems: List<ToolbarIcons>,
    modifier: Modifier = Modifier,
    toolbarIconModifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val firstItem = listOfToolbarItems.get(0)
        val secondItem = listOfToolbarItems.get(1)

        Image(
            painter = painterResource(id = firstItem.iconRes),
            contentDescription = null,
            modifier = toolbarIconModifier
                .alpha(firstItemAlpha)
                .size(62.dp)
                .clickable(
                    enabled = firstItem.isShown
                ) {
                    onItemClicked(firstItem.name)
                }
        )

        if (isGameScreen == true) {
            BalanceCard(
                modifier = Modifier,
                money = money
            )
        }

        Image(
            painter = painterResource(id = secondItem.iconRes),
            contentDescription = null,
            modifier = toolbarIconModifier
                .alpha(secondItemAlpha)
                .size(62.dp)
                .clickable(
                    enabled = secondItem.isShown
                ) {
                    onItemClicked(secondItem.name)
                }
        )
    }
}

@Composable
fun BalanceCard(modifier: Modifier, money: Int) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, iconCoin, moneyTextField) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.field_balance_hdpi),
            modifier = Modifier
                .height(80.dp)
                .width(170.dp)
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.ic_coin_hdpi),
            modifier = Modifier
                .size(50.dp)
                .constrainAs(iconCoin) {
                    top.linkTo(box.top)
                    start.linkTo(box.start)
                    bottom.linkTo(box.bottom)
                },
            contentDescription = null
        )
        OutlinedText(
            text = stringResource(id = R.string.x, money),
            fontSize = 20.sp,
            modifier = Modifier
                .constrainAs(moneyTextField) {
                    top.linkTo(box.top)
                    start.linkTo(box.start)
                    end.linkTo(box.end)
                    bottom.linkTo(box.bottom)
                }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameToolbarPreview() {
    val shopItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SHOP.name }
    val settingsItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.LOBBY.name }

    val listOfItems = mutableListOf(shopItem, settingsItem)

//    BalanceCard(modifier = Modifier, money = 50)
    GameToolbar(
        modifier = Modifier,
        isGameScreen = false,
        listOfToolbarItems = listOfItems.toList() as List<ToolbarIcons>,
        onItemClicked = {},
    )
}