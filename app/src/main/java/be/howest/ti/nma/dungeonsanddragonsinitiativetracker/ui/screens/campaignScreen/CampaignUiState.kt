package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class CampaignUiState(
    val campaigns: Flow<List<Campaign>> = MutableStateFlow(emptyList()),
    var selectedCampaignId: Long = -1L

)
