package com.android.candywords.state

import androidx.compose.ui.graphics.Color
import com.android.candywords.R
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.data.Level
import com.android.candywords.data.LevelData
import com.android.candywords.data.SoundOption
import com.android.candywords.data.firstLevel
import com.android.candywords.data.levelItems
import com.android.candywords.data.soundOptions

data class CandyUiState(
    val isAchievementsShown: Boolean = false,
    val isMenuScreenVisible: Boolean = false,
    val isSettingsShown: Boolean = false,
    val connectivityStatus: ConnectivityObserver.Status = ConnectivityObserver.Status.Available,
    val isNoInternetPopupShown: Boolean = false,
    val toolbarIcons: ToolbarIcons = ToolbarIcons.CROSS,
//    val items: List<Item> = listOfChars,
    val levelsData: List<LevelData> = listOf(),
    val levelsLobby: List<Level> = levelItems,
    val isLevelPassed: Boolean = false,
    val currentLevel: LevelData = firstLevel,
    val userSelection: List<Item> = listOf(),
    val money: Int = 0,
    val isMoneyEqualsOrLessZero: Boolean = false,
    val soundOptionsState: List<SoundOption> = soundOptions,
    val color: Int = 0
)

data class Item(
    val id: Int,
    val character: Char,
    val isSelected: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean
)

//val listOfChars = listOf(
//    Item(1, 'A', isSelected = false, isFirst = true, isLast = false),
//    Item(2, 'b', isSelected = false, isFirst = false, isLast = false),
//    Item(3, 'c', isSelected = false, isFirst = false, isLast = false),
//    Item(4, 'd', isSelected = false, isFirst = false, isLast = false),
//    Item(5, 'e', isSelected = false, isFirst = false, isLast = false),
//    Item(6, 'f', isSelected = false, isFirst = false, isLast = false),
//    Item(7, 'j', isSelected = false, isFirst = false, isLast = false),
//    Item(11, 'k', isSelected = false, isFirst = false, isLast = false),
//    Item(13, 'l', isSelected = false, isFirst = false, isLast = false),
//    Item(100, 'A', isSelected = false, isFirst = false, isLast = false),
//    Item(290, 'b', isSelected = false, isFirst = false, isLast = false),
//    Item(300, 'c', isSelected = false, isFirst = false, isLast = false),
//    Item(43, 'd', isSelected = false, isFirst = false, isLast = false),
//    Item(54, 'e', isSelected = false, isFirst = false, isLast = false),
//    Item(65, 'f', isSelected = false, isFirst = false, isLast = false),
//    Item(735, 'j', isSelected = false, isFirst = false, isLast = false),
//    Item(1153, 'k', isSelected = false, isFirst = false, isLast = false),
//    Item(1353, 'l', isSelected = false, isFirst = false, isLast = false),
//    Item(443, 'd', isSelected = false, isFirst = false, isLast = false),
//    Item(504, 'e', isSelected = false, isFirst = false, isLast = false),
//    Item(650, 'f', isSelected = false, isFirst = false, isLast = false),
//    Item(7350, 'j', isSelected = false, isFirst = false, isLast = false),
//    Item(1103, 'k', isSelected = false, isFirst = false, isLast = false),
//    Item(1303, 'l', isSelected = false, isFirst = true, isLast = false)
//)

enum class ToolbarIcons(val isShown: Boolean, val iconRes: Int) {
    LOBBY(isShown = true, iconRes = R.drawable.ic_menu_hdpi),
    SHOP(isShown = true, iconRes = R.drawable.ic_shop),
    CROSS(isShown = true, iconRes = R.drawable.ic_close),
    HOME(isShown = true, iconRes = R.drawable.ic_home),
    SETTINGS(isShown = true, iconRes = R.drawable.ic_settings)
}