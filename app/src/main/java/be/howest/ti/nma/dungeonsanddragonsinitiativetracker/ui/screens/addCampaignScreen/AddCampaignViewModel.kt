package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddCampaignViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddCampaignUiState())
    val uiState: StateFlow<AddCampaignUiState> = _uiState.asStateFlow()

    fun updateCampaignName(name: String) {
        val currentUiState = uiState.value
        _uiState.value = currentUiState.copy(campaignName = name)
    }
}