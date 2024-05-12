package com.android.candywords.state

sealed class CandyUiEvent {
    data class OnSettingsSelected(val selectedItem: Int) : CandyUiEvent()

    object OnMenuScreenVisible : CandyUiEvent()

}