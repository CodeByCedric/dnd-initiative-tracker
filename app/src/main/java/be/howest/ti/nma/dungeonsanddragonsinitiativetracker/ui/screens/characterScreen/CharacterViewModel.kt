package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.PlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class CharacterViewModel(
    private val playerCharacterRepository: PlayerCharacterRepository,
    private val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository,
    private val enemyRepository: EnemyRepository
) : ViewModel() {
    private val _characterUiState = MutableStateFlow(CharacterUiState())
    val characterUiState: StateFlow<CharacterUiState> = _characterUiState.asStateFlow()


    init {
        _characterUiState.value = CharacterUiState(
            primaryCharacters = getPrimaryCharacters(1L),
            secondaryCharacters = getSecondaryCharacters(1L),
            enemies = getEnemies(),

            )
    }

    fun rollInitiative(initiativeModifier: Int): Int {
        return (1..20).random() + initiativeModifier
    }

    fun selectCharacter(character: CampaignPlayerCharacterDetail) {
        val currentSelectedCharacters = _characterUiState.value.selectedCharacters.toMutableList()
        currentSelectedCharacters.add(character)
        _characterUiState.value =
            _characterUiState.value.copy(selectedCharacters = currentSelectedCharacters)
    }

    fun deselectCharacter(character: CampaignPlayerCharacterDetail) {
        val currentSelectedCharacters = _characterUiState.value.selectedCharacters.toMutableList()
        currentSelectedCharacters.remove(character)
        _characterUiState.value =
            _characterUiState.value.copy(selectedCharacters = currentSelectedCharacters)
    }

    fun updateInitiativeForCharacters(
        playerCharacter: CampaignPlayerCharacterDetail,
        initiative: Int
    ) {
        val isPrimaryCharacter = playerCharacter.isPrimaryCharacter

        val characterFlow = if (isPrimaryCharacter) {
            _characterUiState.value.primaryCharacters
        } else {
            _characterUiState.value.secondaryCharacters
        }

        val updatedCharacters = characterFlow.map { characters
            ->
            characters.map { character ->
                if (character == playerCharacter) {
                    character.copy(initiative = initiative)
                } else {
                    character
                }
            }
        }

        if (isPrimaryCharacter) {
            _characterUiState.value =
                _characterUiState.value.copy(primaryCharacters = updatedCharacters)
        } else {
            _characterUiState.value =
                _characterUiState.value.copy(secondaryCharacters = updatedCharacters)
        }


    }


    private fun getPrimaryCharacters(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterRepository.getCampaignPrimaryCharactersWithDetails(campaignId)
    }

    private fun getSecondaryCharacters(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterRepository.getCampaignSecondaryCharactersWithDetails(campaignId)
    }


    private fun getEnemies(): Flow<List<Enemy>> {
        return enemyRepository.getAllEnemies()
    }

}