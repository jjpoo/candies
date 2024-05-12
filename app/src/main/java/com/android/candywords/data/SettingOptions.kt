package com.android.candywords.data

data class SettingOption(
    val name: String,
    val isSelected: Boolean = false
)

enum class SettingOptionsNames {
    SOUND,
    MUSIC,
    VIBRATION
}