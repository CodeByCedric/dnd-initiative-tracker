package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant

interface CampaignParticipantRepository {
    suspend fun insertCampaignParticipant(campaignParticipant: CampaignParticipant): Long

    suspend fun insertAllCampaignParticipants(campaignParticipants: List<CampaignParticipant>):
            List<Long>

    suspend fun deleteCampaignParticipant(campaignParticipant: CampaignParticipant)
//    fun getCampaignParticipantsForCampaignStream(campaignId: Int): Flow<List<Participant>>
//    Todo implement this function
}