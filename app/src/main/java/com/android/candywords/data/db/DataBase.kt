package com.android.candywords.data.db

import com.android.candywords.data.Candy
import com.android.candywords.state.Item

val candies = listOf(
    Candy(
        1,
        listOf('c', 'a', 'n', 'd', 'y'),
        false
    ),
    Candy(
        2,
        listOf('s', 'w', 'e', 'e', 't'),
        false
    ),
    Candy(
        3,
        listOf('s', 'u', 'g', 'a', 'r'),
        false
    )
)
val candiesLevelFirst = candies

val charactersLevelFirst = listOf(
    Item(1, 'c', isSelected = false, isFirst = true, isLast = false),
    Item(2, 'a', isSelected = false, isFirst = false, isLast = false),
    Item(3, 'n', isSelected = false, isFirst = false, isLast = false),
    Item(4, 'd', isSelected = false, isFirst = false, isLast = false),
    Item(5, 'y', isSelected = false, isFirst = false, isLast = false),
    Item(6, 'c', isSelected = false, isFirst = false, isLast = false),
    Item(7, 't', isSelected = false, isFirst = false, isLast = false),
    Item(8, 's', isSelected = false, isFirst = false, isLast = false),
    Item(9, 'w', isSelected = false, isFirst = false, isLast = false),
    Item(10, 'e', isSelected = false, isFirst = false, isLast = false),
    Item(11, 'e', isSelected = false, isFirst = false, isLast = false),
    Item(12, 't', isSelected = false, isFirst = false, isLast = false),
    Item(13, 'l', isSelected = false, isFirst = false, isLast = false),
    Item(14, 'f', isSelected = false, isFirst = false, isLast = false),
    Item(15, 's', isSelected = false, isFirst = false, isLast = false),
    Item(16, 'o', isSelected = false, isFirst = false, isLast = false),
    Item(17, 'b', isSelected = false, isFirst = false, isLast = false),
    Item(18, 'a', isSelected = false, isFirst = false, isLast = false),
    Item(19, 's', isSelected = false, isFirst = false, isLast = false),
    Item(20, 'u', isSelected = false, isFirst = false, isLast = false),
    Item(21, 'g', isSelected = false, isFirst = false, isLast = false),
    Item(22, 'a', isSelected = false, isFirst = false, isLast = false),
    Item(23, 'r', isSelected = false, isFirst = false, isLast = false),
    Item(24, 'k', isSelected = false, isFirst = true, isLast = false)
)
