package com.android.candywords.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.candywords.state.ToolbarIcons

@Composable
fun GameToolbar(
    listOfToolbarItems: List<ToolbarIcons>,
    modifier: Modifier = Modifier,
    toolbarIconModifier: Modifier = Modifier,
    onToolbarItemClicked: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOfToolbarItems.forEach { icon ->

            Image(
                painter = painterResource(id = icon.iconRes),
                contentDescription = null,
                modifier = toolbarIconModifier
                    .size(62.dp)
                    .clickable(
                        enabled = icon.isShown
                    ) {
                        onToolbarItemClicked(icon.name)
                    }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameToolbarPreview() {
    GameToolbar(
        modifier = Modifier,
        listOfToolbarItems = listOf(),
        onToolbarItemClicked = {},
    )
}