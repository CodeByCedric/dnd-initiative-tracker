package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetail

data class SkirmishUiState(
    val sortedListOfSkirmishCharacters: List<CampaignPlayerCharacterDetail> = emptyList()
)