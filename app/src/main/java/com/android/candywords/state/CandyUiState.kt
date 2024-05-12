package com.android.candywords.state

import com.android.candywords.R
import com.android.candywords.data.SettingOption
import com.android.candywords.data.SettingOptionsNames

data class CandyUiState(
    val isAchievementsShown: Boolean = false,
    val settings: List<SettingOption> = listOfSettings,
    val isMenuScreenVisible: Boolean = false
)

val listOfSettings = listOf(
    SettingOption(
        name = SettingOptionsNames.MUSIC.name,
        isSelected = false
    ),
    SettingOption(
        name = SettingOptionsNames.SOUND.name,
        isSelected = false
    ),
    SettingOption(
        name = SettingOptionsNames.VIBRATION.name,
        isSelected = false
    )
)

enum class ToolbarIcons(val isShown: Boolean, val iconRes: Int) {
    MENU(isShown = false, iconRes = R.drawable.union),
    SHOP(isShown = true, iconRes = R.drawable.ic_shop),
    CROSS(isShown = false, iconRes = R.drawable.ic_close),
    HOME(isShown = true, iconRes = R.drawable.ic_home),
    SETTINGS(isShown = true, iconRes = R.drawable.ic_settings)
}