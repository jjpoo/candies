package com.android.candywords.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.android.candywords.R

val textStyleOutlined = TextStyle.Default.copy(
    fontSize = 25.sp,
    textAlign = TextAlign.Center,
    fontFamily = FontFamily(Font(R.font.titan_one_regular)),
    fontWeight = FontWeight(400),
    lineHeight = TextUnit(22.9F, TextUnitType.Unspecified),
    drawStyle = Stroke(
        miter = 10f,
        width = 1f,
        join = StrokeJoin.Miter
    )
)

val textStyleFilled = TextStyle.Default.copy(
    fontWeight = FontWeight(400),
    fontFamily = FontFamily(Font(R.font.titan_one_regular)),
    lineHeight = TextUnit(22.9F, TextUnitType.Unspecified),
    fontSize = 25.sp,
    color = Color.White,
    textAlign = TextAlign.Center
)


@Composable
fun OutlinedText(
    modifier: Modifier = Modifier,
    styleFill: TextStyle = textStyleFilled,
    styleOutlined: TextStyle = textStyleOutlined,
    text: String,
    lineHeight: TextUnit = TextUnit(22.9F, TextUnitType.Unspecified),
    textAlign: TextAlign = TextAlign.Left,
    fontSize: TextUnit = 25.sp
) {
    Box(
        modifier = modifier
    ) {
        Text(
            lineHeight = lineHeight,
            text = text,
            fontSize = fontSize,
            style = styleFill,
            textAlign = textAlign
        )
        Text(
            lineHeight = lineHeight,
            text = text,
            style = styleOutlined,
            textAlign = textAlign,
            fontSize = fontSize,
            color = colorResource(id = R.color.purple_outline)
        )
    }
}

@Preview
@Composable
fun OutlinedTexNewVPreview() {
    OutlinedText(
        text = "Text"
    )
}