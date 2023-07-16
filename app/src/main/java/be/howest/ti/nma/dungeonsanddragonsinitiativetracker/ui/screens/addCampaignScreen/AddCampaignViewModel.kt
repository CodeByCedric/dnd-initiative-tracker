package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    fun getParticipants(): List<Participant> {
        return addCampaignUiState.value.playerList
    }

    fun save() {
        saveCampaign()
        /*todo
        * saveCampaign() -> return campaignId
        * saveParticipants() -> return campaignParticipantIds
        * saveCampaignParticipants() -> use campaignId and campaignParticipantsIds to store the
        * campaignParticipants in the intermediate table
        * */

    }

    private fun saveCampaign() {
        val campaign = Campaign(
            campaignName = addCampaignUiState.value.campaignName,
            campaignImageUri = addCampaignUiState.value.campaignImageUri
        )
        viewModelScope.launch {
            campaignRepository.insertCampaign(campaign)
        }
    }

    fun updateParticipantName(
        name: String,
        isDungeonMaster: Boolean = false,
    ) {
        val currentUiState = addCampaignUiState.value
        if (isDungeonMaster) {
            val updatedDungeonMasterName = currentUiState.dungeonMaster.copy(participantName = name)
            _addCampaignUiState.value =
                currentUiState.copy(dungeonMaster = updatedDungeonMasterName)
        } else {
            val updatedPlayerName = currentUiState.player.copy(participantName = name)
            _addCampaignUiState.value = currentUiState.copy(player = updatedPlayerName)
        }
    }

    fun updateParticipantEmail(
        email: String,
        isDungeonMaster: Boolean = false,
    ) {
        val currentUiState = addCampaignUiState.value
        if (isDungeonMaster) {
            val updatedDungeonMasterEmail = currentUiState.dungeonMaster.copy(email = email)
            _addCampaignUiState.value =
                currentUiState.copy(dungeonMaster = updatedDungeonMasterEmail)
        } else {
            val updatedPlayerEmail = currentUiState.player.copy(email = email)
            _addCampaignUiState.value = currentUiState.copy(player = updatedPlayerEmail)
        }
    }

    fun updatePlayerList() {
        val currentUiState = addCampaignUiState.value
        val currentPlayer = currentUiState.player

        val updatedPlayerList = currentUiState.playerList + currentPlayer

        _addCampaignUiState.value = currentUiState.copy(
            playerList = updatedPlayerList,
            player = Participant(
                participantName = "",
                email = ""
            )
        )
    }

    fun removePlayerFromPlayerList(participant: Participant) {
        val currentUiState = addCampaignUiState.value
        val updatedPlayerList = currentUiState.playerList - participant

        _addCampaignUiState.value = currentUiState.copy(
            playerList = updatedPlayerList
        )
    }

}
