package com.android.candywords

import androidx.lifecycle.ViewModel
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
            is CandyUiEvent.OnSettingsSelected -> handleOnSettingsSelected(uiEvent.selectedItem)
        }
    }

    private fun handleOnMenuScreenVisible() {
        _state.value = _state.value.copy(
            isMenuScreenVisible = !_state.value.isMenuScreenVisible
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
        _state.value = _state.value.copy(settings = updatedOptions)
    }
}