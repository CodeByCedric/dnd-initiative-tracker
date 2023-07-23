package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class CharacterUiState(
    val isInitialized: Boolean = false,
    val primaryCharacters: Flow<List<CampaignPlayerCharacterDetail>> = MutableStateFlow(emptyList()),
    val secondaryCharacters: Flow<List<CampaignPlayerCharacterDetail>> = MutableStateFlow(emptyList()),
    val enemyCharacters: Flow<List<CampaignPlayerCharacterDetail>> = MutableStateFlow(emptyList()),
    val allEnemies: Flow<List<Enemy>> = MutableStateFlow(emptyList()),
    val selectedCharacters: MutableList<CampaignPlayerCharacterDetail> = mutableListOf(),
    val searchQuery: String = "",
)

