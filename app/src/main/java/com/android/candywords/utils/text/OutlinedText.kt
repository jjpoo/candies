package com.android.candywords.utils.text

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Paint as ComposePaint

val textPaintStroke = ComposePaint().asFrameworkPaint().apply {
    isAntiAlias = true
    style = Paint.Style.STROKE
    textSize = 100f
    color = Color.BLACK
    strokeWidth = 12f
    strokeMiter = 10f
    strokeJoin = Paint.Join.ROUND
}

val textPaint = ComposePaint().asFrameworkPaint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    textSize = 100f
    color = Color.WHITE
}

@Composable
fun OutlinedText(
    text: String
) {
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    text,
                    200f,
                    200.dp.toPx(),
                    textPaintStroke
                )
                
                it.nativeCanvas.drawText(
                    text,
                    200f,
                    200.dp.toPx(),
                    textPaint
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextPreview() {
    OutlinedText(text = "TExtxtet")
}