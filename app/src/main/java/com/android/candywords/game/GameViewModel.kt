package com.android.candywords.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.candywords.R
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
            is CandyUiEvent.UpdateColorForOneItem -> updateItemColor(uiEvent.itemId)
            else -> {}
        }
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

    private fun handleSetUpLevel() {
        val levels = _state.value.levelsData
        _state.value = _state.value.copy(
            currentLevel = requireNotNull(levels.find { it.isRecentlyPlayed })
        )
    }

    private fun updateLevelState() {
        val currentLevel = _state.value.currentLevel

        val updatedLevel = currentLevel.copy(
            isCompleted = !_state.value.currentLevel.isCompleted
        )
        if (_state.value.currentLevel.listOfCandies.all { it.isOpened }) {
            _state.value = _state.value.copy(
                currentLevel = updatedLevel
            )
        }
        Log.e("is level completed", "${updatedLevel.isCompleted}")
    }

    private fun handleUpdateCandyOpenState(indexToUpdate: Int) {

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

    private fun handleSelectedCharacters(selectedCharacters: List<Char>) {
        _state.value.currentLevel.listOfCandies.mapIndexed { index, candy ->
            val isStringsEquals = candy.name == selectedCharacters
            if (isStringsEquals) {
                handleUpdateCandyOpenState(index)
                when (index) {
                    0 -> {
                        updateCurrentColor(color = R.color.candy2_background)
                    }

                    1 -> {
                        updateCurrentColor(color = R.color.candy3_background)
                    }
                }
                updateNiceGoodFineState()
            }
        }
        updateLevelState()
        Log.e("Level State", "${_state.value.currentLevel.listOfCandies.map { it.isOpened }}")
    }

    private fun updateNiceGoodFineState() {
        val candyState = _state.value.currentLevel.listOfCandies
        candyState.forEachIndexed { index, candy ->
            if (candy.isOpened) {
                when (index) {
                    0 -> {
                        _state.value = _state.value.copy(
                            niceFineGoodState = R.drawable.text_nice_mdpi
                        )
                    }

                    1 -> {
                        _state.value = _state.value.copy(
                            niceFineGoodState = R.drawable.text_fine_mdpi
                        )
                    }

                    2 -> {
                        _state.value = _state.value.copy(
                            niceFineGoodState = R.drawable.text_good_mdpi
                        )
                    }
                }
            }
        }
    }

    /** Item Color **/
    private fun updateItemColor(itemId: Int) {

        val colorId = if (_state.value.color != 0) {
            _state.value.color
        } else {
            R.color.candy1_background
        }

        val updatedCharacters = _state.value.currentLevel.characters.map {
            if (it.id == itemId) {
                it.copy(color = colorId)
            } else {
                it
            }
        }

        val updatedLevel = _state.value.currentLevel.copy(characters = updatedCharacters)

        _state.value = _state.value.copy(
            currentLevel = updatedLevel
        )
    }

    private fun updateCurrentColor(color: Int) {
        _state.value = _state.value.copy(
            color = color
        )
    }

    /** Item Color **/

    /** Item Shape **/
    private fun handleUpdateFirstItemShape(firstCharId: Int) {
        viewModelScope.launch {
            val listWithRoundedCornerItems = _state.value.currentLevel.characters.map { item ->
                if (firstCharId == item.id) {
                    item.copy(isFirst = !item.isFirst)
                } else item
            }
            val updatedCurrentLevelValue =
                _state.value.currentLevel.copy(characters = listWithRoundedCornerItems)
            _state.value = _state.value.copy(currentLevel = updatedCurrentLevelValue)
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
    /** Item Shape **/
}
