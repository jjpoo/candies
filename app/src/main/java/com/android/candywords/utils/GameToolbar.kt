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
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons

@Composable
fun GameToolbar(
    modifier: Modifier = Modifier,
    leftButtonModifier: Modifier = Modifier,
    state: CandyUiState,
    onLeftIconClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ToolbarIcons.values().forEach {
            if (it.isShown) {
                Image(
                    painter = painterResource(id = it.iconRes),
                    contentDescription = null,
                    modifier = leftButtonModifier
                        .size(62.dp)
                        .clickable(
                            enabled = !state.isAchievementsShown
                        ) {
                            onLeftIconClicked()
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameToolbarPreview() {
    GameToolbar(
        modifier = Modifier,
        state = CandyUiState(),
        onLeftIconClicked = {},
    )
}