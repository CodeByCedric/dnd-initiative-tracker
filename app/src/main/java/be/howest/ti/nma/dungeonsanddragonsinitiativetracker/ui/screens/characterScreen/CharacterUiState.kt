package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Enemy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CharacterUiState(
    val primaryCharacters: Flow<List<CampaignPlayerCharacterDetails>> = MutableStateFlow(emptyList()),
    val secondaryCharacters: Flow<List<CampaignPlayerCharacterDetails>> = MutableStateFlow(emptyList()),
    val enemies: Flow<List<Enemy>> = MutableStateFlow(emptyList()),
)