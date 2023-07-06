package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen

import android.net.Uri
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.R

data class AddCampaignUiState(
    var campaignName: String = "",
    val campaignDrawable: Int = R.drawable.placeholder_view_vector,
    var campaignImageUri: Uri? = null,

    )