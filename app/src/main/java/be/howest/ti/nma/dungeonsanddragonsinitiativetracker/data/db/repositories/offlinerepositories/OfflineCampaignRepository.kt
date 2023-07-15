package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.Flow

class OfflineCampaignRepository(
    private val campaignDao: CampaignDao
) : CampaignRepository {
    override suspend fun insertCampaign(campaign: Campaign): Long {
        return campaignDao.insert(campaign)
    }

    override suspend fun insertAllCampaigns(campaigns: List<Campaign>): List<Long> {
        return campaignDao.insertAll(campaigns)
    }


    override suspend fun deleteCampaign(campaign: Campaign) {
        campaignDao.delete(campaign)
    }

    override fun getCampaign(campaignId: Long): Flow<Campaign?> {
        return campaignDao.getCampaign(campaignId)
    }

    override fun getAllCampaigns(): Flow<List<Campaign>> {
        return campaignDao.getAllCampaigns()
    }

    override suspend fun getRowCount(): Int {
        return campaignDao.getRowCount()
    }

    override suspend fun updateDateTimeOfNextSession(
        campaignId: Long,
        selectedDateTimeAsLong: Long?
    ) {
        campaignDao.updateCampaignDateTime(
            campaignId,
            selectedDateTimeAsLong
        )
    }

}
