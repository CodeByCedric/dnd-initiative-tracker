package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.SkirmishCharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SkirmishViewModel(
    private val skirmishCharacterRepository: SkirmishCharacterRepository
) : ViewModel() {
    private val _skirmishUiState = MutableStateFlow(SkirmishUiState())
    val skirmishUiState: StateFlow<SkirmishUiState> = _skirmishUiState.asStateFlow()


    init {
        viewModelScope.launch {
            skirmishCharacterRepository.getAllSkirmishCharacters()
                .collect { listOfSkirmishCharacters ->
                    _skirmishUiState.value = SkirmishUiState(
                        sortedListOfSkirmishCharacters = getSortedListOfCharacters(listOfSkirmishCharacters)
                    )
                }
        }
    }

    private fun getSortedListOfCharacters(listOfSkirmishCharacters: List<SkirmishCharacter>): List<SkirmishCharacter> {
        return listOfSkirmishCharacters.sortedWith(
            compareByDescending<SkirmishCharacter> { it.initiative }
                .thenByDescending { it.initiativeModifier }
                .thenByDescending { Math.random() }
        )
    }

    private suspend fun getListOfSkirmishCharacters(): List<SkirmishCharacter> {
        val flowOfSkirmishCharacters = skirmishCharacterRepository.getAllSkirmishCharacters()
        val listOfSkirmishCharacters = flowOfSkirmishCharacters.first().toList()
        Log.d(
            "listOfSkirmishCharacters",
            listOfSkirmishCharacters.toString()
        )
        return listOfSkirmishCharacters
    }

    fun updateSortedCharacters(sortedListOfSkirmishCharacters: List<SkirmishCharacter>) {
        val newState =
            _skirmishUiState.value.copy(sortedListOfSkirmishCharacters = sortedListOfSkirmishCharacters)
        _skirmishUiState.value = newState
    }

    fun deleteSkirmishCharacter(skirmishCharacter: SkirmishCharacter) {
        val currentUiState = skirmishUiState.value
        val updatedCharacters =
            currentUiState.sortedListOfSkirmishCharacters - skirmishCharacter
        _skirmishUiState.value =
            currentUiState.copy(sortedListOfSkirmishCharacters = updatedCharacters)
    }

    fun getBackgroundCardColor(skirmishCharacter: SkirmishCharacter): Color {
        if (skirmishCharacter.isPrimaryCharacter) {
            return Color(0xFFbafffe)
        } else if (skirmishCharacter.isSecondaryCharacter) {
            return Color(0xFFddffba)
        } else if (skirmishCharacter.isEnemy) {
            return Color(0xFFffbabb)
        } else {
            return Color.Transparent
        }
    }


}