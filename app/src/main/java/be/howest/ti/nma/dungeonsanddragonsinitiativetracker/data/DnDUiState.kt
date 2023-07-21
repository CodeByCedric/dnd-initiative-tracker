package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


//Start of all encompassing UiState to pass along as parameter between screen
//Will not implement further due to uncertainty of usefulness of approach and time constraints
data class DnDUiState(
    val campaigns: Flow<List<Campaign>> = MutableStateFlow(emptyList()),
    val selectedCampaignId: Long = -1L
)