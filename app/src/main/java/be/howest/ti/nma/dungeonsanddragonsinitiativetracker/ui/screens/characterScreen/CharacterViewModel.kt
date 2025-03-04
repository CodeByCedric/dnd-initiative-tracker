package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.SkirmishCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignPlayerCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.EnemyRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.SkirmishCharacterRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class CharacterViewModel(
    private val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository,
    private val enemyRepository: EnemyRepository,
    private val skirmishCharacterRepository: SkirmishCharacterRepository
) : ViewModel() {
    private val _characterUiState = MutableStateFlow(CharacterUiState())
    val characterUiState: StateFlow<CharacterUiState> = _characterUiState.asStateFlow()


//    init {
//        _characterUiState.value = CharacterUiState(
//            primaryCharacters = getPrimaryCharacters(1L),
//            secondaryCharacters = getSecondaryCharacters(1L),
//            enemies = getEnemies(),
//
//            )
//    }

    fun updateIsInitialized(isInitialized: Boolean) {
        val currentUiState = characterUiState.value
        _characterUiState.value = currentUiState.copy(isInitialized = isInitialized)
    }

    fun loadCharacters(campaignId: Long) {
        _characterUiState.value = CharacterUiState(
            primaryCharacters = getPrimaryCharacters(campaignId),
            secondaryCharacters = getSecondaryCharacters(campaignId),
            enemyCharacters = getEnemies(campaignId),
            allEnemies = getAllEnemies()
        )
    }


    fun rollInitiative(initiativeModifier: Int): String {
        val initiative = (1..20).random() + initiativeModifier
        return initiative.toString()
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
        initiative: String
    ) {
        val isPrimaryCharacter = playerCharacter.isPrimaryCharacter
        val isSecondaryCharacter = playerCharacter.isSecondaryCharacter
        val isEnemy = playerCharacter.isEnemy

        val characterFlow = if (isPrimaryCharacter) {
            _characterUiState.value.primaryCharacters
        } else if (isSecondaryCharacter) {
            _characterUiState.value.secondaryCharacters
        } else {
            _characterUiState.value.enemyCharacters
        }

        val updatedCharacters = characterFlow.map { characters ->
            characters.map { character ->
                if (character == playerCharacter) {
                    character.copy(initiative = initiative.toIntOrNull())
                } else {
                    character
                }
            }
        }

        if (isPrimaryCharacter) {
            _characterUiState.value =
                _characterUiState.value.copy(primaryCharacters = updatedCharacters)
        }
        if (isSecondaryCharacter) {
            _characterUiState.value =
                _characterUiState.value.copy(secondaryCharacters = updatedCharacters)
        }
        if (isEnemy) {
            _characterUiState.value =
                _characterUiState.value.copy(enemyCharacters = updatedCharacters)
        }
    }

    private fun getPrimaryCharacters(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterRepository.getCampaignPrimaryCharactersWithDetails(campaignId)
    }

    private fun getSecondaryCharacters(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterRepository.getCampaignSecondaryCharactersWithDetails(campaignId)
    }

    private fun getEnemies(campaignId: Long): Flow<List<CampaignPlayerCharacterDetail>> {
        return campaignPlayerCharacterRepository.getCampaignEnemyCharactersWithDetails(campaignId)
    }

    private fun getAllEnemies(): Flow<List<Enemy>> {
        return enemyRepository.getAllEnemies()
    }

    suspend fun deleteCharacter(
        campaignId: Long,
        playerCharacter: CampaignPlayerCharacterDetail
    ) {
        campaignPlayerCharacterRepository.deleteCampaignPlayerCharacter(
            CampaignPlayerCharacter(
                playerCharacterId = playerCharacter.playerCharacterId,
                campaignId = campaignId
            )
        )
    }

    suspend fun clearSkirmishCharacterTable() {
        skirmishCharacterRepository.deleteAllSkirmishCharacters()
    }

    //TODO elvis operator on initiative is om de int? te omzeilen, CampaignPlayerCharacterDetail
    // moet uit het project, en vervangen worden door skirmishCharacter
    suspend fun insertSkirmishParticipants(selectedCharacters: MutableList<CampaignPlayerCharacterDetail>) {
        val listOfSkirmishCharacters: MutableList<SkirmishCharacter> = mutableListOf()
        selectedCharacters.forEach { campaignPlayerCharacter ->
            val skirmishCharacter = SkirmishCharacter(
                name = campaignPlayerCharacter.name,
                armorClass = campaignPlayerCharacter.armorClass,
                initiativeModifier = campaignPlayerCharacter.initiativeModifier,
                initiative = campaignPlayerCharacter.initiative ?: 10,
                isPrimaryCharacter = campaignPlayerCharacter.isPrimaryCharacter,
                isSecondaryCharacter = campaignPlayerCharacter.isSecondaryCharacter,
                isEnemy = campaignPlayerCharacter.isEnemy,
            )
            listOfSkirmishCharacters.add(skirmishCharacter)
        }

        skirmishCharacterRepository.insertListOfSkirmishCharacters(listOfSkirmishCharacters)
    }


}

