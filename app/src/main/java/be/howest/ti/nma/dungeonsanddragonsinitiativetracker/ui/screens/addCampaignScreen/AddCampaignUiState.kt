package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant

data class AddCampaignUiState(
    var campaignName: String = "",
    val campaignDrawable: Int = R.drawable.placeholder_view_vector,
    var campaignImageUri: Uri? = null,
    var dungeonMaster: Participant = Participant(
        participantName = "",
        email = "",
        isDungeonMaster = true
    ),
    var playerList: List<Participant> = emptyList(),
    var player: Participant = Participant(
        participantName = "",
        email = ""
    )

)