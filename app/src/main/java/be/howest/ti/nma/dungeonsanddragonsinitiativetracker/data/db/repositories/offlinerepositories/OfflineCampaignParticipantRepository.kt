package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignParticipantDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.models.CampaignParticipantDetails
import kotlinx.coroutines.flow.Flow

class OfflineCampaignParticipantRepository(
    private val campaignParticipantDao: CampaignParticipantDao
) : CampaignParticipantRepository {
    override suspend fun insertCampaignParticipant(campaignParticipant: CampaignParticipant): Long {
        return campaignParticipantDao.insert(campaignParticipant)
    }

    override suspend fun insertAllCampaignParticipants(
        campaignParticipants:
        List<CampaignParticipant>
    ): List<Long> {
        return campaignParticipantDao.insertAll(campaignParticipants)
    }

    override suspend fun deleteCampaignParticipant(campaignParticipant: CampaignParticipant) {
        campaignParticipantDao.delete(campaignParticipant)
    }

    override fun getCampaignParticipantsForCampaign(campaignId: Long): Flow<List<CampaignParticipant>> {
        return campaignParticipantDao.getCampaignParticipantsForCampaign(campaignId)
    }

    override fun getCampaignParticipantsWithDetails(campaignId: Long): Flow<List<CampaignParticipantDetails>> {
        return campaignParticipantDao.getCampaignParticipantsWithDetails(campaignId)
    }

}