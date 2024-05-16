package com.android.candywords.game.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.candywords.state.Item
import com.android.candywords.utils.OutlinedText

@Composable
fun LetterItem(
    modifier: Modifier,
//    onClick: () -> Unit,
    item: Item,
    color: Int,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    isSelected: Boolean
) {
    Log.e("SELECTED", "$isSelected")
    Log.e("COLOR", "${color}")
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(
                if (item.isFirst) RoundedCornerShape(
                    topStartPercent = 15,
                    bottomStartPercent = 15
                ) else if (isLast) RoundedCornerShape(
                    topEnd = 15.dp,
                    bottomEnd = 15.dp
                ) else RoundedCornerShape(0.dp)
            )
            .background(
                if (isSelected) colorResource(id = item.color).copy(alpha = 0.5f)
                else Color.Transparent
            )
    ) {
        OutlinedText(
            modifier = Modifier.align(Alignment.Center),
            text = item.character.toString().uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            lineHeight = TextUnit(35.35F, TextUnitType.Unspecified)
        )
    }
}
