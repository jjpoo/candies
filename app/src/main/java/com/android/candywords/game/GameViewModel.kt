package com.android.candywords.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun handleSelectedCharacters(selectedCharacters: List<Int>) {

        val listOfCharsByIds = _state.value.currentLevel.characters.filter {
            it.id in selectedCharacters
        }.filter { !it.isSelected }
        Log.e("list of items", "${listOfCharsByIds}")

        _state.value = _state.value.copy(
            personSelection = listOfCharsByIds
        )

        val string = listOfCharsByIds.map {
            it.character
        }
        Log.e("STRING", "$string")

        _state.value.currentLevel.listOfCandies.map { candy ->
            val isStringsEquals = candy.name == string
            if (isStringsEquals) {
                updateCharacterSelectedState()
                Log.e("Personal Selection", "${_state.value.personSelection}")
            }
        }


//        val compareCharacters =
//        val compareCharacters = unswears.map { candyUnswear ->
//            val isWordsEquals = candyUnswear.name.compareTo(selectedWord, ignoreCase = true)
//            if (isWordsEquals == 0) {
//                candyUnswear.copy(
//                    isOpened = !candyUnswear.isOpened
//                )
//            } else {
//                candyUnswear
//            }
//        }
//        val updatedCurrentLevelState =
//            _state.value.currentLevel.copy(listOfCandies = compareCharacters)
//        _state.value = _state.value.copy(
//            currentLevel = updatedCurrentLevelState
//        )
    }

    private fun updateCharacterSelectedState() {
        val personalSelection = _state.value.personSelection
        val listWithUpdatedSelection = personalSelection.map {
            it.copy(
                isSelected = !it.isSelected
            )
        }
        _state.value = _state.value.copy(
            personSelection = listWithUpdatedSelection
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
