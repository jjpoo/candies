package com.android.candywords.data

import com.android.candywords.R

data class Level(
    val number: Int,
    val imageRes: Int,
    val isOpened: Boolean,
    val isComingSoon: Boolean
)


val levelItems = listOf(
    Level(1, R.drawable.ic_level_1_hdpi, false, false),
    Level(2, R.drawable.ic_level_2_hdpi, false, false),
    Level(3, R.drawable.ic_level_3_hdpi, false, false),
    Level(4, R.drawable.ic_level_4_hdpi, false, false),
    Level(5, R.drawable.ic_level_5_hdpi, false, false),
    Level(6, R.drawable.ic_level_6_hdpi, false, false),
    Level(7, R.drawable.ic_level_4_hdpi, false, true),
    Level(8, R.drawable.ic_level_5_hdpi, false, true),
    Level(9, R.drawable.ic_level_6_hdpi, false, true)
)
