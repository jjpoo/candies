package com.android.candywords

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.data.Level
import com.android.candywords.data.SettingOption
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(CandyUiState())
    val state: StateFlow<CandyUiState> = _state.asStateFlow()

    fun handleUiEvent(uiEvent: CandyUiEvent) {
        when (uiEvent) {
            CandyUiEvent.OnMenuScreenVisible -> handleOnMenuScreenVisible()
            CandyUiEvent.OnSettingsClicked -> handleOnSettingsClicked()
            CandyUiEvent.UpdateLevelCompletition -> updateLevelState()
            is CandyUiEvent.UpdateToolbarButtonState -> {}
            is CandyUiEvent.ShowNoInternetConnectionPopup -> handleShowNoInternetConnectionPopup(
                uiEvent.showPopup
            )
            is CandyUiEvent.OnSettingsSelected -> handleOnSettingsSelected(uiEvent.selectedItem)
            is CandyUiEvent.OpenLevel -> handleOpenLevel(uiEvent.number)
            is CandyUiEvent.UpdateLevelsState -> handleUpdateLevelState(uiEvent.levels)
            else -> {}
        }
    }

    /** Connectivity **/
    private fun handleShowNoInternetConnectionPopup(showPopup: Boolean) {
        _state.value = _state.value.copy(
            isNoInternetPopupShown = showPopup
        )
    }

    fun updateConnectivityStatus(status: ConnectivityObserver.Status) {
        _state.value = _state.value.copy(
            connectivityStatus = status
        )
    }

    /** Connectivity **/

    private fun handleOnSettingsClicked() {
        _state.value = _state.value.copy(
            isSettingsShown = !_state.value.isSettingsShown
        )
    }

    private fun handleOnSettingsSelected(selectedItem: Int) {
        val updatedOptions = _state.value.settings.mapIndexed { index, settingOption ->
            if (selectedItem == index) {
                settingOption.copy(
                    isSelected = !settingOption.isSelected
                )
            } else settingOption
        }
        Log.e("SAVED ITEMS", "$updatedOptions")
        _state.value = _state.value.copy(settings = updatedOptions)
    }

    fun setSettings(settings: List<SettingOption>) {
        _state.value = _state.value.copy(
            settings = settings
        )
    }

    private fun handleOnMenuScreenVisible() {
        _state.value = _state.value.copy(
            isMenuScreenVisible = !_state.value.isMenuScreenVisible
        )
    }

    private fun handleOpenLevel(openedLevelNumber: Int) {

        val listWithUpdatedLevel = _state.value.levelsLobby.map { level ->
            if (openedLevelNumber == level.number) {
                level.copy(isOpened = !level.isOpened)
            } else {
                level
            }
        }
        Log.e(
            "updated level",
            "$listWithUpdatedLevel"
        )
        _state.value = _state.value.copy(levelsLobby = listWithUpdatedLevel)
        updateLevelState()
    }


    /** Levels in Lobby **/

    // Trigger this fun when level will be passed
    private fun updateLevelState() {
        _state.value = _state.value.copy(
            isLevelPassed = !_state.value.isLevelPassed
        )
    }

    private fun handleUpdateLevelState(levels: List<Level>) {
        _state.value = _state.value.copy(levelsLobby = levels)
    }

    /** Levels in Lobby **/
}