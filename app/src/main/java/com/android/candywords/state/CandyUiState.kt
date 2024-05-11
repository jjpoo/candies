package com.android.candywords.state

import com.android.candywords.R

data class CandyUiState(
    val isAchievementsShown: Boolean = false
)

enum class ToolbarIcons(val isShown: Boolean, val iconRes: Int) {
    MENU(isShown = false, iconRes = R.drawable.union),
    SHOP(isShown = true, iconRes = R.drawable.ic_shop),
    CROSS(isShown = false, iconRes = R.drawable.ic_close),
    HOME(isShown = true, iconRes = R.drawable.ic_home)
}