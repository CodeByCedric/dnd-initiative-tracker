package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant

@Dao
interface CampaignParticipantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(campaignParticipant: CampaignParticipant)

    @Delete
    suspend fun delete(campaignParticipant: CampaignParticipant)


//    @Query("SELECT * FROM campaignParticipants")
//    fun getCampaignParticipantsForCampaign(campaignId: Int): Flow<List<Participant>>
//    todo set correct query
}