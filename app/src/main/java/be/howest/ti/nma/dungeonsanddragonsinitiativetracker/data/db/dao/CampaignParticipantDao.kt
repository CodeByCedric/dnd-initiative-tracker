package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipant
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignParticipantDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignParticipantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(campaignParticipant: CampaignParticipant): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(campaignParticipant: List<CampaignParticipant>): List<Long>

    @Delete
    suspend fun delete(campaignParticipant: CampaignParticipant)

    @Query("SELECT * FROM campaignParticipants WHERE campaignId = :campaignId")
    fun getCampaignParticipantsForCampaign(campaignId: Long): Flow<List<CampaignParticipant>>

    @Query(
        "SELECT cp.campaignId, cp.participantId, p.participantName, p.email, p.isDungeonMaster FROM " +
                "campaignParticipants as cp INNER JOIN participants as p on " +
                "cp.participantId = p.participantId WHERE cp.campaignId = :campaignId"
    )
    fun getCampaignParticipantsWithDetails(campaignId: Long): Flow<List<CampaignParticipantDetails>>


}