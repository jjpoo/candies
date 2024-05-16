package com.android.candywords.utils

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.data.Candy
import com.android.candywords.data.candiesLevelFirst

@Composable
fun WordsBlock(
    modifier: Modifier = Modifier,
    candies: List<Candy>
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
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            candies.forEach { candy ->
                if (candy.isOpened) {
                    TextWithShadow(
                        candy = candy
                    )
                } else
                    OutlinedText(
                        modifier = Modifier,
                        lineHeight = TextUnit(17.18F, TextUnitType.Unspecified),
                        text = candy.name.forEach { it.uppercase() }.toString(),
                        fontSize = 15.sp
                    )
            }
        }
    }
}

@Composable
fun TextWithShadow(
    candy: Candy,
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.purple_outline)
) {
    Box(
        modifier = Modifier
    ) {
        ConstraintLayout {
            val (text, line) = createRefs()

            OutlinedText(
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                lineHeight = TextUnit(17.18F, TextUnitType.Unspecified),
                text = candy.name.forEach { it.uppercase() }.toString(),
                fontSize = 15.sp
            )

            Box(
                modifier = Modifier
                    .constrainAs(line) {
                        start.linkTo(text.start)
                        end.linkTo(text.end)
                        top.linkTo(text.top)
                        bottom.linkTo(text.bottom)
                    }
            ) {
                Canvas(modifier = Modifier) {
                    drawLine(
                        color = color,
                        start = Offset(-70f, 0f),
                        end = Offset(70f, 0f),
                        strokeWidth = 7f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WordsBlockPreview() {
    WordsBlock(
        candies = candiesLevelFirst
    )
}
