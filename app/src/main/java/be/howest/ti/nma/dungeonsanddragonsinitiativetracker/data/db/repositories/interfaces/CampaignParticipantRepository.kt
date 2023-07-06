package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant

interface CampaignParticipantRepository {
    suspend fun insertCampaignParticipant(campaignParticipant: CampaignParticipant)
    suspend fun deleteCampaignParticipant(campaignParticipant: CampaignParticipant)
//    fun getCampaignParticipantsForCampaignStream(campaignId: Int): Flow<List<Participant>>
//    Todo implement this function
}