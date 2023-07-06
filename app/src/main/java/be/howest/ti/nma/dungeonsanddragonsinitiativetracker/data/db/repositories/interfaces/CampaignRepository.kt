package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {
    suspend fun insertCampaign(campaign: Campaign)
    suspend fun deleteCampaign(campaign: Campaign)
    fun getCampaignStream(campaignId: Int): Flow<Campaign?>
    fun getAllCampaignsStrean(): Flow<List<Campaign>>

}