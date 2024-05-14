package com.android.candywords.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.candywords.R

@Composable
fun WordsBlock(
    modifier: Modifier = Modifier,
    candies: List<String>
) {
    Box(
        modifier = modifier.height(90.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.block_words_hdpi),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        Row(
            modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            candies.forEach { candy ->
                OutlinedText(
                    lineHeight = TextUnit(17.18F, TextUnitType.Unspecified),
                    text = candy.uppercase(),
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun WordsBlockPreview() {
    WordsBlock(
        candies = listOf("Candy", "Sweet", "Sugar")
    )
}
