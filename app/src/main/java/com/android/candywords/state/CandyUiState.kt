package com.android.candywords.state

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
    val levelsData: List<LevelData> = listOf(),
    val levelsLobby: List<Level> = levelItems,
    val isLevelPassed: Boolean = false,
    val currentLevel: LevelData = firstLevel,
    val userSelection: List<Item> = listOf(),
    val money: Int = 0,
    val isMoneyEqualsOrLessZero: Boolean = false,
    val soundOptionsState: List<SoundOption> = soundOptions,
    val color: Int = 0,
    val niceFineGoodState: Int = 0
)

data class Item(
    val id: Int,
    val character: Char,
    val isSelected: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean,
    val color: Int
)

enum class ToolbarIcons(val isShown: Boolean, val iconRes: Int) {
    LOBBY(isShown = true, iconRes = R.drawable.ic_menu_hdpi),
    SHOP(isShown = true, iconRes = R.drawable.ic_shop),
    CROSS(isShown = true, iconRes = R.drawable.ic_close),
    HOME(isShown = true, iconRes = R.drawable.ic_home),
    SETTINGS(isShown = true, iconRes = R.drawable.ic_settings)
}