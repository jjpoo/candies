package com.android.candywords.state

import com.android.candywords.data.Level

sealed class CandyUiEvent {
    object OnMenuScreenVisible : CandyUiEvent()
    object OnSettingsClicked : CandyUiEvent()
    object UpdateLevelCompletition : CandyUiEvent()
    object SetUpLevel : CandyUiEvent()
    data class OnSettingsSelected(val selectedItem: Int) : CandyUiEvent()
    data class ShowNoInternetConnectionPopup(val showPopup: Boolean) : CandyUiEvent()
    data class UpdateToolbarButtonState(val toolbarItem: String) : CandyUiEvent()
    data class UpdateFirstItemCornerRadius(val fisrtItemId: Int) : CandyUiEvent()
    data class UpdateLastItemCornerRadius(val secondItemId: Int) : CandyUiEvent()
    data class OpenLevel(val number: Int) : CandyUiEvent()
    data class UpdateLevelsState(val levels: List<Level>) : CandyUiEvent()
    data class GetSelectedCharacters(val listOfChars: List<Int>) : CandyUiEvent()
    data class OnSoundSelected(val selectedSoundId: Int) : CandyUiEvent()
}