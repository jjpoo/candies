package com.android.candywords.game

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.candywords.data.Level
import com.android.candywords.data.SoundOption
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _state = MutableStateFlow(CandyUiState())
    val state: StateFlow<CandyUiState> = _state.asStateFlow()

    fun handleUiEvent(uiEvent: CandyUiEvent) {
        when (uiEvent) {
            CandyUiEvent.SetUpLevel -> handleSetUpLevel()
            is CandyUiEvent.UpdateFirstItemCornerRadius -> handleUpdateFirstItemShape(uiEvent.fisrtItemId)
            is CandyUiEvent.UpdateLastItemCornerRadius -> handleUpdateLastItemShape(uiEvent.secondItemId)
            is CandyUiEvent.GetSelectedCharacters -> handleSelectedCharacters(uiEvent.listOfChars)
            is CandyUiEvent.UpdateCurrentColor -> updateCurrentColor(uiEvent.color)
            else -> {}
        }
    }

    private fun handleUpdateFirstItemShape(fisrtItemId: Int) {
        viewModelScope.launch {

            val listWithRoundedCornerItems = _state.value.currentLevel.characters.map { item ->
                if (fisrtItemId == item.id) {
                    item.copy(
                        isFirst = !item.isFirst
                    )
                } else item
            }
            val updatedCurrentLevelValue =
                _state.value.currentLevel.copy(characters = listWithRoundedCornerItems)

            _state.value = _state.value.copy(currentLevel = updatedCurrentLevelValue)

            Log.e(
                "rounded items:",
                "${listWithRoundedCornerItems.get(fisrtItemId)}"
            )
        }
    }

    private fun handleUpdateLastItemShape(secondItemId: Int) {
        val updateLastItem = _state.value.currentLevel.characters.map { item ->
            if (secondItemId == item.id) {
                item.copy(
                    isLast = !item.isLast
                )
            } else item
        }
        val updatedCurrentLevelValue = _state.value.currentLevel.copy(characters = updateLastItem)
        _state.value = _state.value.copy(currentLevel = updatedCurrentLevelValue)
    }

    private fun handleSetUpLevel() {
        val levels = _state.value.levelsData
        _state.value = _state.value.copy(
            currentLevel = requireNotNull(levels.find { it.isRecentlyPlayed })
        )
    }

    private fun handleSelectedCharacters(selectedCharacters: List<Char>) {
        _state.value.currentLevel.listOfCandies.mapIndexed { index, candy ->
            val isStringsEquals = candy.name == selectedCharacters
            if (isStringsEquals) {
                updateResultState(index)
            }
            Log.e("Is items equals", "$isStringsEquals")
        }
    }

    private fun updateLevelState() {
        val currentLevel = _state.value.currentLevel

        val updatedLevel = currentLevel.copy(
            isCompleted = true
        )
        if (_state.value.currentLevel.listOfCandies.all { it.isOpened }) {
            _state.value = _state.value.copy(
                currentLevel = updatedLevel
            )
        }
    }
    private fun updateResultState(indexToUpdate: Int) {

        val candies = _state.value.currentLevel.listOfCandies

        val updatedCandyState = candies.mapIndexed { index, candy ->
            if (index == indexToUpdate) {
                candy.copy(
                    isOpened = !candy.isOpened
                )
            } else candy
        }
        val updatedCurrentLevel = _state.value.currentLevel.copy(
            listOfCandies = updatedCandyState
        )
        _state.value = _state.value.copy(
            currentLevel = updatedCurrentLevel
        )
        Log.e("UPDATED ITEM CANDY", "$updatedCandyState")
    }


    private fun updateCurrentColor(color: Color) {
        _state.value = _state.value.copy(
            color = color
        )
    }

    fun updateMoneyState(money: Int) {
        if (money <= 0) {
            _state.value = _state.value.copy(
                isMoneyEqualsOrLessZero = true
            )
        } else {
            _state.value = _state.value.copy(
                money = money
            )
        }
    }

    fun setSettings(settings: List<SoundOption>) {
        _state.value = _state.value.copy(
            soundOptionsState = settings
        )
    }
}
