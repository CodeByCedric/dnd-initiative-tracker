package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddCampaignViewModel(
    private val campaignRepository: CampaignRepository
) : ViewModel() {
    private val _addCampaignUiState = MutableStateFlow(AddCampaignUiState())
    val addCampaignUiState: StateFlow<AddCampaignUiState> = _addCampaignUiState.asStateFlow()
    fun updateCampaignName(name: String) {
        val currentUiState = addCampaignUiState.value
        _addCampaignUiState.value = currentUiState.copy(campaignName = name)
    }

    fun updateCampaignImage(uri: Uri) {
        val currentUiState = addCampaignUiState.value
        _addCampaignUiState.value = currentUiState.copy(campaignImageUri = uri)
    }

    fun getCampaignImage(): Uri? {
        return addCampaignUiState.value.campaignImageUri
    }

    fun getCampaignName(): String {
        return addCampaignUiState.value.campaignName
    }


    fun save() {
        /*todo
        * saveCampaign() -> return campaignId
        * saveParticipants() -> return campaignParticipantIds
        * saveCampaignParticipants() -> use campaignId and campaignParticipantsIds to store the
        * campaignParticipants in the intermediate table
        * */

    }
//    private fun saveCampaign(){
//        val campaign = Campaign(
//            campaignName = addCampaignUiState.value.campaignName,
//            campaignImageUri = addCampaignUiState.value.campaignImageUri
//        )
//        viewModelScope.launch {
//            addCampaignRepository.insertCampaign(campaign)
//        }
//    }
}
