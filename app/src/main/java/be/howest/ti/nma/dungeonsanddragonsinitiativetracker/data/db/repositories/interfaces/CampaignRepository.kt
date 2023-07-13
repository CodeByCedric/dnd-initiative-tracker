package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {
    suspend fun insertCampaign(campaign: Campaign): Long
    suspend fun insertAllCampaigns(campaigns: List<Campaign>): List<Long>
    suspend fun deleteCampaign(campaign: Campaign)
    fun getCampaignStream(campaignId: Long): Flow<Campaign?>
    fun getAllCampaignsStream(): Flow<List<Campaign>>
    suspend fun getRowCount(): Int

}