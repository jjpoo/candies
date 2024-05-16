package com.android.candywords.data

import com.android.candywords.data.db.candiesLevelFirst
import com.android.candywords.data.db.charactersLevelFirst
import com.android.candywords.state.Item

data class LevelData(
    val columnsCount: Int,
    val characters: List<Item>,
    val listOfCandies: List<Candy>,
    val isCompleted: Boolean,
    val isRecentlyPlayed: Boolean
)

data class Candy(
    val id: Int,
    val name: List<Char>,
    val isOpened: Boolean
)

val firstLevel = LevelData(
    columnsCount = 6,
    characters = charactersLevelFirst,
    listOfCandies = candiesLevelFirst,
    isCompleted = false,
    isRecentlyPlayed = true
)

val secondLevel = LevelData(
    columnsCount = 6,
    characters = charactersLevelFirst,
    listOfCandies = candiesLevelFirst,
    isCompleted = false,
    isRecentlyPlayed = true
)

