package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.offlinerepositories

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao.CampaignParticipantDao
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces.CampaignParticipantRepository

class OfflineCampaignParticipantRepository(private val campaignParticipantDao: CampaignParticipantDao) :
    CampaignParticipantRepository {
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

//    override fun getCampaignParticipantsForCampaignStream(campaignId: Int):
//            Flow<List<Participant>> =
//        campaignParticipantDao.getCampaignParticipantsForCampaign(campaignId)
// Todo implement this function
}