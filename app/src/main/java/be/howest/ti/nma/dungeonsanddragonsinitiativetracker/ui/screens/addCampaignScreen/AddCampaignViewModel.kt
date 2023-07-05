package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddCampaignViewModel : ViewModel() {
    private val _addCampaignUiState = MutableStateFlow(AddCampaignUiState())
    val addCampaignUiState: StateFlow<AddCampaignUiState> = _addCampaignUiState.asStateFlow()

    fun createCampaignReturnId(): Int {
        val currentUiState = addCampaignUiState.value
        val campaign = Campaign(campaignName = currentUiState.campaignName)
        if (currentUiState.campaignImageUri != null) {
            campaign.campaignImageUri = currentUiState.campaignImageUri
        }
        //todo insert campaign in DB
        return campaign.campaignId
    }
}