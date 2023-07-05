package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignRepository
import kotlinx.coroutines.flow.Flow

class OfflineCampaignRepository(
    private val campaignDao: CampaignDao.CampaignDao,
//    private val campaignDaoIncorrect: CampaignDao
// TODO waar komt deze vandaan? linken naar dezelfde dao file, er is maar 1 CampaignDao, maar
//  campaignDaoIncorrect werkt niet
) :
    CampaignRepository {
    override suspend fun insertCampaign(campaign: Campaign) {
        campaignDao.insert(campaign)
    }

    override suspend fun deleteCampaign(campaignId: Int) {
        campaignDao.delete(campaignId)
    }

    override fun getCampaignStream(campaignId: Int): Flow<Campaign?> {
        return campaignDao.getCampaign(campaignId)
    }

    override fun getAllCampaignsStrean(): Flow<List<Campaign>> {
        return campaignDao.getAllCampaigns()
    }
}
