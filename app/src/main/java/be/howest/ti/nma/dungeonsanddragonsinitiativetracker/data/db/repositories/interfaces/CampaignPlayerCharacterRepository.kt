package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter

interface CampaignPlayerCharacterRepository {
    suspend fun insertCampaignPlayerCharacter(campaignPlayerCharacter: CampaignPlayerCharacter): Long

}