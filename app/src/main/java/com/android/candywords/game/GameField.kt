package com.android.candywords.game

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toIntRect
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.data.LevelData
import com.android.candywords.data.candies
import com.android.candywords.data.charactersLevelFirst
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.Item
import com.android.candywords.utils.OutlinedText

@Composable
fun GameField(
    modifier: Modifier = Modifier,
    uiState: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit
) {
    val state = rememberLazyGridState()
    val selectedIdSet = rememberSaveable {
        mutableStateOf(emptySet<Int>())
    }
    val inSelectionMode by remember { derivedStateOf { selectedIdSet.value.isNotEmpty() } }

    Box(modifier = Modifier.fillMaxSize()) {

        ConstraintLayout {
            val (image, grid) = createRefs()

            Image(
                painter = painterResource(R.drawable.field_game_1_hdpi),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
                    .fillMaxSize()
            )

            LazyVerticalGrid(
                state = state,
                columns = GridCells.Fixed(6),
                modifier = modifier
                    .charactersDragHandler(
                        lazyGridState = state,
                        selectedIdSet = selectedIdSet,
                        uiEvent = uiEvent
                    )
                    .constrainAs(grid) {
                        start.linkTo(image.start)
                        bottom.linkTo(image.bottom)
                        top.linkTo(image.top)
                        end.linkTo(image.end)
                    }
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(
                    uiState.currentLevel.characters,
                    key = { it.id }) { item ->

                    val selected by remember { derivedStateOf { selectedIdSet.value.contains(item.id) } }

                    LetterItem(
                        item = item,
                        isSelected = selected,
                        modifier = Modifier
                    )
//                            .semantics {
//                                if (!inSelectionMode) {
//                                    onLongClick("Select") {
//                                        selectedIdSet.value += item.id
//                                        true
//                                    }
//                                }
//                            }
//                            .then(
//                                if (inSelectionMode) {
//                                    Modifier.toggleable(
//                                        value = item.isSelected,
//                                        indication = null,
//                                        interactionSource = remember { MutableInteractionSource() },
//                                        onValueChange = {
//                                            if (it) {
//                                                selectedIdSet.value += item.id
//                                            } else {
//                                                selectedIdSet.value -= item.id
//                                            }
//                                        }
//                                    )
//                                } else Modifier
//                            ))
                }
            }
        }
    }
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.charactersDragHandler(
    lazyGridState: LazyGridState,
    selectedIdSet: MutableState<Set<Int>>,
    uiEvent: (CandyUiEvent) -> Unit
) = pointerInput(Unit) {

    fun LazyGridState.gridItemKeyAtPosition(hitPoint: Offset): Int? =
        layoutInfo.visibleItemsInfo.find { itemInfo ->
            itemInfo.size.toIntRect().contains(hitPoint.round() - itemInfo.offset)
        }?.key as? Int

    var initialKey: Int? = null
    var currentKey: Int? = null

    detectDragGestures(
        onDragStart = { offset ->
            lazyGridState.gridItemKeyAtPosition(offset)?.let { key ->
                if (!selectedIdSet.value.contains(key)) {
                    initialKey = key
                    currentKey = key
                    selectedIdSet.value += key
                }
            }
            Log.e("CURRENT KEY THAT IS First", "$initialKey")
            uiEvent(CandyUiEvent.UpdateFirstItemCornerRadius(fisrtItemId = selectedIdSet.value.first()))
        },
        onDrag = { change, dragAmount ->
            if (initialKey != null) {
                val (x, y) = dragAmount

                val absX = Math.abs(x)
                val absY = Math.abs(y)

                val tolerance = 0.2f
                val distanceRatio = absX / (absX + absY)

                val isPureHorizontal = absY <= tolerance
                val isPureVertical = absX <= tolerance

                val isDiagonal = !isPureHorizontal && !isPureVertical &&
                        (distanceRatio < (1 - tolerance) || distanceRatio > (1 + tolerance));

//                if (!isDiagonal) {
                Log.e("PURE VALUE", "x:$absX y:$absY")
                lazyGridState.gridItemKeyAtPosition(change.position)?.let { key ->
                    if (currentKey != null) {

                        selectedIdSet.value = selectedIdSet.value
                            .plus(currentKey!!)

                        if (selectedIdSet.value.contains(key)) {
                            selectedIdSet.value = selectedIdSet.value
                                .minus(currentKey!!)
                        }
                        Log.e("CURRENT KEY THAT IS LAST", "$currentKey")
                        currentKey = key
                    }
                }
            }
        },
        onDragCancel = {
            initialKey = null
//            uiEvent(CandyUiEvent.UpdateLastItemCornerRadius(secondItemId = selectedIdSet.value.last()))
        },
        onDragEnd = {
            Log.e("CURRENT KEY THAT IS LAST", "${selectedIdSet.value.last()}")
            uiEvent(CandyUiEvent.UpdateLastItemCornerRadius(secondItemId = selectedIdSet.value.last()))
            initialKey = null
        }
    )
}

@Composable
fun LetterItem(
    modifier: Modifier,
//    onClick: () -> Unit,
    item: Item,
    isSelected: Boolean
) {
    //test purpose , remove later
    val isSeletedTest = item.isSelected
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(
                if (item.isFirst) RoundedCornerShape(15, 0, 0, 15) else RoundedCornerShape(
                    0.dp,
                    0.dp,
                    0.dp,
                    0.dp
                )
            )
            .then(
                Modifier.clip(
                    if (item.isLast) RoundedCornerShape(
                        0.dp,
                        15.dp,
                        15.dp,
                        0.dp
                    ) else RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                )
            )
            .background(if (isSelected) Color.Magenta else Color.Transparent)
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GameFieldPreview() {

    Column(modifier = Modifier.fillMaxSize()) {
        GameField(
            uiState = CandyUiState(
                currentLevel = LevelData(
                    6,
                    charactersLevelFirst,
                    candies,
                    isCompleted = false,
                    isRecentlyPlayed = true
                )
            ),
            uiEvent = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GameItem() {
    LetterItem(modifier = Modifier, item = Item(1, 'C', true, true, false), isSelected = false)
}
