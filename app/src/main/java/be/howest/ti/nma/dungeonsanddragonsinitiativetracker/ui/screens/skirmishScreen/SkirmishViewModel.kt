package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.DataSource.skirkmishScreenCharacters
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SkirmishViewModel(
) : ViewModel() {
    private val _skirmishUiState = MutableStateFlow(SkirmishUiState())
    val skirmishUiState: StateFlow<SkirmishUiState> = _skirmishUiState.asStateFlow()


    init {
        _skirmishUiState.value = SkirmishUiState(
            sortedListOfSkirmishCharacters = getSortedListOfCharacters(skirkmishScreenCharacters)
        )
    }

    private fun getSortedListOfCharacters(listOfSkirmishCharacters: List<CampaignPlayerCharacterDetail>): List<CampaignPlayerCharacterDetail> {
        return listOfSkirmishCharacters.sortedWith(
            compareByDescending<CampaignPlayerCharacterDetail> { it.initiative }
                .thenByDescending { it.initiativeModifier }
                .thenByDescending { Math.random() }
        )
    }

    fun updateSortedCharacters(sortedListOfSkirmishCharacters: List<CampaignPlayerCharacterDetail>) {
        val newState =
            _skirmishUiState.value.copy(sortedListOfSkirmishCharacters = sortedListOfSkirmishCharacters)
        _skirmishUiState.value = newState
    }

    fun deleteSkirmishCharacter(campaignPlayerCharacter: CampaignPlayerCharacterDetail) {
        val currentUiState = skirmishUiState.value
        val updatedCharacters =
            currentUiState.sortedListOfSkirmishCharacters - campaignPlayerCharacter
        _skirmishUiState.value =
            currentUiState.copy(sortedListOfSkirmishCharacters = updatedCharacters)
    }

    fun getBackgroundCardColor(campaignPlayerCharacter: CampaignPlayerCharacterDetail): Color {
        if (campaignPlayerCharacter.isPrimaryCharacter) {
            return Color(0xFFbafffe)
        } else if (campaignPlayerCharacter.isSecondaryCharacter) {
            return Color(0xFFddffba)
        } else if (campaignPlayerCharacter.isEnemy) {
            return Color(0xFFffbabb)
        } else {
            return Color.Transparent
        }
    }


}