package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipantDetails
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CampaignViewModel(
    private val campaignRepository: CampaignRepository,
    private val campaignParticipantRepository: CampaignParticipantRepository
) : ViewModel() {
    private val _campaignUiState = MutableStateFlow(CampaignUiState())
    val campaignUiState: StateFlow<CampaignUiState> = _campaignUiState.asStateFlow()

    init {
        _campaignUiState.value = CampaignUiState(
            campaigns = getCurrentCampaigns(),
        )
    }

    private fun getCurrentCampaigns(): Flow<List<Campaign>> {
        return campaignRepository.getAllCampaigns()
    }

    fun getCampaignParticipantsWithDetails(campaignId: Long): Flow<List<CampaignParticipantDetails>> {
        return campaignParticipantRepository.getCampaignParticipantsWithDetails(campaignId)

    }

    suspend fun deleteCampaign(campaign: Campaign) {
        campaignRepository.deleteCampaign(campaign = campaign)

    }

    suspend fun deleteParticipant(campaignParticipant: CampaignParticipant) {
        campaignParticipantRepository.deleteCampaignParticipant(campaignParticipant)

    }

}