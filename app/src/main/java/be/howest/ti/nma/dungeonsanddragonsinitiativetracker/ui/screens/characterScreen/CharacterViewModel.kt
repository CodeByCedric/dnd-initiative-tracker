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

class CharacterViewModel(
    private val playerCharacterRepository: PlayerCharacterRepository,
    private val campaignPlayerCharacterRepository: CampaignPlayerCharacterRepository,
    private val enemyRepository: EnemyRepository
) : ViewModel() {
    private val _characterUiState = MutableStateFlow(CharacterUiState())
    val characterUiState: StateFlow<CharacterUiState> = _characterUiState.asStateFlow()

//    init {
//        _characterUiState.value = CharacterUiState(
//            primaryCharacters = getPrimaryCharacters(campaignId),
//            secondaryCharacters = getSecondaryCharacters(campaignId),
//            enemies = getEnemies(),
//
//            )
//    }


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