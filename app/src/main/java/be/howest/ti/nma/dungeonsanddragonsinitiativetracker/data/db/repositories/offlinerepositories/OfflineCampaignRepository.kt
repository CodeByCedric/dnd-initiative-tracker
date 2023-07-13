package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.Flow

class OfflineCampaignRepository(private val campaignDao: CampaignDao) : CampaignRepository {
    override suspend fun insertCampaign(campaign: Campaign) {
        campaignDao.insert(campaign)
    }

    override suspend fun deleteCampaign(campaign: Campaign) {
        campaignDao.delete(campaign)
    }

    override fun getCampaignStream(campaignId: Long): Flow<Campaign?> {
        return campaignDao.getCampaign(campaignId)
    }

    override fun getAllCampaignsStream(): Flow<List<Campaign>> {
        return campaignDao.getAllCampaigns()
    }

}
