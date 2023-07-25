package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.DnDInitiativeTrackerApplication
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.addCampaignScreen.AddCampaignViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.campaignScreen.CampaignViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.characterScreen.CharacterViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.createCharacterScreen.CreateCharacterViewModel
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.ui.screens.skirmishScreen.SkirmishViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CampaignViewModel(
                dndInitiativeTrackerApplication().container.campaignRepository,
                dndInitiativeTrackerApplication().container.campaignParticipantRepository
            )
        }
        initializer {
            AddCampaignViewModel(
                dndInitiativeTrackerApplication().container.campaignRepository,
                dndInitiativeTrackerApplication().container.participantRepository,
                dndInitiativeTrackerApplication().container.campaignParticipantRepository
            )
        }
        initializer {
            CharacterViewModel(
                dndInitiativeTrackerApplication().container.playerCharacterRepository,
                dndInitiativeTrackerApplication().container.campaignPlayerCharacterRepository,
                dndInitiativeTrackerApplication().container.enemyRepository
            )
        }
        initializer {
            CreateCharacterViewModel(
                dndInitiativeTrackerApplication().container.playerCharacterRepository,
                dndInitiativeTrackerApplication().container.campaignPlayerCharacterRepository,
                dndInitiativeTrackerApplication().container.enemyRepository
            )
        }
        initializer {
            SkirmishViewModel(
            )
        }
    }
}

fun CreationExtras.dndInitiativeTrackerApplication(): DnDInitiativeTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DnDInitiativeTrackerApplication)