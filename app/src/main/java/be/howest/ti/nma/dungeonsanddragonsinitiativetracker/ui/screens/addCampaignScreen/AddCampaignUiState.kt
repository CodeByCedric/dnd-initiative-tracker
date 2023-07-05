package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant

data class AddCampaignUiState(
    val campaignName: String = "",
    var campaignIconUri: Uri? = null,
    val dungeonMasterName: String = "",
    val dungeonMasterEmail: String = "",
    val participants: List<Participant> = listOf(
        Participant(
            participantName = "",
            email = "",
            isDungeonMaster = false
        )
    ),

)