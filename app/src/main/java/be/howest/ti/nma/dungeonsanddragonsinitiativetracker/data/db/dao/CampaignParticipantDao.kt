package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Participant
import kotlinx.coroutines.flow.Flow

interface CampaignParticipantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(campaignParticipant: CampaignParticipant)

    @Delete
    suspend fun delete(campaignParticipant: CampaignParticipant)

    @Query("SELECT * FROM campaignParticipants")
    fun getCampaignParticipantsForCampaign(campaignId: Int): Flow<List<Participant>>
    //todo set correct query
}