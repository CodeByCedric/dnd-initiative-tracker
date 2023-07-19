package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.ParticipantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCampaignViewModel(
    private val campaignRepository: CampaignRepository,
    private val participantRepository: ParticipantRepository,
    private val campaignParticipantRepository: CampaignParticipantRepository,
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

    fun getParticipants(): List<Participant> {
        return addCampaignUiState.value.playerList
    }

    fun save() {
        viewModelScope.launch {
            val campaignId = saveCampaign()
            val participantIds = saveParticipants()
            saveCampaignParticipants(
                campaignId,
                participantIds
            )
        }
    }

    private suspend fun saveCampaignParticipants(
        campaignId: Long,
        participantIds: List<Long>
    ) {
        participantIds.forEach { participantId ->
            val campaignParticipant = CampaignParticipant(
                campaignId = campaignId,
                participantId = participantId
            )
            campaignParticipantRepository.insertCampaignParticipant(campaignParticipant)
        }

    }

    private suspend fun saveCampaign(): Long {
        val campaign = Campaign(
            campaignName = addCampaignUiState.value.campaignName,
            campaignImageUri = addCampaignUiState.value.campaignImageUri
        )
        return campaignRepository.insertCampaign(campaign)
    }

    private suspend fun saveParticipants(): List<Long> {
        val participants = addCampaignUiState.value.playerList
        return participantRepository.insertAllParticipants(participants)
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

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}".toRegex()
        return emailPattern.matches(email)
    }

    fun validatePlayerInputFields(): Boolean {
        val currentUiState = addCampaignUiState.value
        return currentUiState.player.participantName.isNotBlank() && isEmailValid(currentUiState.player.email)
    }

    private fun validateDungeonMasterInputFields(): Boolean {
        val currentUiState = addCampaignUiState.value
        return currentUiState.dungeonMaster.participantName.isNotBlank() && isEmailValid(
            currentUiState
                .dungeonMaster.email
        )
    }

    private fun validateCampaignName(): Boolean {
        val currentUiState = addCampaignUiState.value
        return currentUiState.campaignName.isNotBlank()
    }

    private fun isPlayerListEmpty(): Boolean {
        val currentUiState = addCampaignUiState.value
        return currentUiState.playerList.isNotEmpty()
    }
}
