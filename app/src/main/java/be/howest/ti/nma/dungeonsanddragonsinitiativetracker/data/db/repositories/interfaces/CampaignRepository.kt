package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {
    suspend fun insertCampaign(campaign: Campaign): Long
    suspend fun insertAllCampaigns(campaigns: List<Campaign>): List<Long>
    suspend fun deleteCampaign(campaign: Campaign)
    fun getCampaign(campaignId: Long): Flow<Campaign?>
    fun getAllCampaigns(): Flow<List<Campaign>>
    suspend fun getRowCount(): Int
    suspend fun updateDateTimeOfNextSession(
        campaignId: Long,
        selectedDateTimeAsLong: Long?
    )

}