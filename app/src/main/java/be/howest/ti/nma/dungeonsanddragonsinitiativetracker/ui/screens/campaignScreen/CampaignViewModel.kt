package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipantDetails
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    fun updateCampaignDateTime(
        campaignId: Long,
        selectedDateTime: Long
    ) {
        val formattedDateTime = formatDateTime(selectedDateTime)
        viewModelScope.launch {
            campaignRepository.updateDateTimeOfNextSession(
                campaignId,
                formattedDateTime
            )
        }
    }

    private fun formatDateTime(dateTime: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = dateTime
        }
        val dateFormat = SimpleDateFormat("dd/MM")
        val timeFormat = SimpleDateFormat("HH:mm")
        val date = dateFormat.format(calendar.time)
        val time = timeFormat.format(calendar.time)
        return "$date - $time"
    }

}