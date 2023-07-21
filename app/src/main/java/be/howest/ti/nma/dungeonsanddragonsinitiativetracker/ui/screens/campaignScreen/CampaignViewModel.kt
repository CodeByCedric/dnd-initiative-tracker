package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignParticipantDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CampaignViewModel(
    private val campaignRepository: CampaignRepository,
    private val campaignParticipantRepository: CampaignParticipantRepository
) : ViewModel() {
//    private val _dndUiState = MutableStateFlow(DnDUiState())
//    val dndUiState: StateFlow<DnDUiState> = _dndUiState.asStateFlow()
//
//    init {
//        _dndUiState.value = DnDUiState(
//            campaigns = getCurrentCampaigns()
//        )
//    }
//
//    fun setSelectedCampaignId(campaignId: Long) {
//        val currentUiState = dndUiState.value
//        _dndUiState.value = currentUiState.copy(selectedCampaignId = campaignId)
//    }
//
//    private fun getCurrentCampaigns(): Flow<List<Campaign>> {
//        return campaignRepository.getAllCampaigns()
//    }


    private val _campaignUiState = MutableStateFlow(CampaignUiState())
    val campaignUiState: StateFlow<CampaignUiState> = _campaignUiState.asStateFlow()

    init {
        _campaignUiState.value = CampaignUiState(
            campaigns = getCurrentCampaigns(),
        )
    }

    fun updateSelectedCampaignId(campaignId: Long) {
        val currentUiState = campaignUiState.value
        _campaignUiState.value = currentUiState.copy(selectedCampaignId = campaignId)
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

    fun updateCampaignDateTime(
        campaignId: Long,
        selectedDateTime: Long
    ) {
        viewModelScope.launch {
            campaignRepository.updateDateTimeOfNextSession(
                campaignId,
                selectedDateTime
            )
        }
    }


}