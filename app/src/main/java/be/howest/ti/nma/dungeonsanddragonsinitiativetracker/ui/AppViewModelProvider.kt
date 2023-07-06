package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerApplication
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        //Initializer for the addCampaignViewModel
        initializer {
            AddCampaignViewModel(
                dndInitiativeTrackerApplication().container.campaignRepository
            )
        }
    }
}

fun CreationExtras.dndInitiativeTrackerApplication(): DnDInitiativeTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DnDInitiativeTrackerApplication)