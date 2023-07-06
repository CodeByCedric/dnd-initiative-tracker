package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository

class AddCampaignViewModel(private val campaignRepository: CampaignRepository) : ViewModel() {

//    private val _addCampaignUiState = MutableStateFlow(AddCampaignUiState())
//    val addCampaignUiState: StateFlow<AddCampaignUiState> = _addCampaignUiState.asStateFlow()

    var addCampaignUiState by mutableStateOf(AddCampaignUiState())
        private set

    fun save() {
        TODO("Not yet implemented")
    }
}