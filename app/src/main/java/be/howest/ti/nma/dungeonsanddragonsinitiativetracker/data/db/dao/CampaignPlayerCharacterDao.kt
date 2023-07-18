package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.CampaignPlayerCharacter

@Dao
interface CampaignPlayerCharacterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(campaignPlayerCharacter: CampaignPlayerCharacter): Long
}