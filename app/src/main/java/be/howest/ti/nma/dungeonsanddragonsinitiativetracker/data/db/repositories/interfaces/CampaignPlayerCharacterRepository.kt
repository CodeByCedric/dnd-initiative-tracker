package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignPlayerCharacterDetails
import kotlinx.coroutines.flow.Flow

interface CampaignPlayerCharacterRepository {
    suspend fun insertCampaignPlayerCharacter(campaignPlayerCharacter: CampaignPlayerCharacter): Long
    suspend fun insertAllCampaignPlayerCharacters(
        campaignPlayerCharacters:
        List<CampaignPlayerCharacter>
    ): List<Long>

    fun getCampaignPrimaryCharactersWithDetails(campaignId: Long):
            Flow<List<CampaignPlayerCharacterDetails>>

    fun getCampaignSecondaryCharactersWithDetails(campaignId: Long):
            Flow<List<CampaignPlayerCharacterDetails>>


}