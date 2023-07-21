package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class DnDUiState(
    val campaigns: Flow<List<Campaign>> = MutableStateFlow(emptyList()),
    val selectedCampaignId: Long = -1L
)