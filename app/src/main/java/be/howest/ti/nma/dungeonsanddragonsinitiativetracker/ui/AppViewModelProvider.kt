package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerApplication

//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//        //Initializer for the addCampaignViewModel
//        initializer {
//            AddCampaignViewModel(
//                this.createSavedStateHandle(),
//                dndInitiativeTrackerApplication().container.
//
//            )
//        }
//    }
//}

fun CreationExtras.dndInitiativeTrackerApplication(): DnDInitiativeTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DnDInitiativeTrackerApplication)