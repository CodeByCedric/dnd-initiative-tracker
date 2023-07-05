package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.repositories.interfaces

import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import kotlinx.coroutines.flow.Flow

interface CampaignParticipantRepository {
    suspend fun insertCampaignParticipant(campaignParticipant: CampaignParticipant)
    suspend fun deleteCampaignParticipant(campaignParticipant: CampaignParticipant)
    fun getCampaignParticipantsForCampaignStream(campaignId: Int): Flow<List<Participant>>
}