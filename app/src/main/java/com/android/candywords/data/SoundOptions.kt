package com.android.candywords.data

data class SoundOption(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

enum class SettingOptionsNames {
    SOUND,
    MUSIC,
    VIBRATION
}