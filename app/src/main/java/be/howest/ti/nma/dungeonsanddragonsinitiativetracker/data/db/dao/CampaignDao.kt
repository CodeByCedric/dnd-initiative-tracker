package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.Campaign
import kotlinx.coroutines.flow.Flow

interface CampaignDao {
    @Dao
    interface CampaignDao {
        //Todo, use a better conflictstrategy
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(campaign: Campaign)
        /* Mark the function with the suspend keyword to let it run on a separate thread.
        * The database operations can take a long time to execute, so they need to run on a separate
        * thread. Room doesn't allow database access on the main thread.*/

        @Delete
        suspend fun delete(campaignId: Int)

        @Query("SELECT * FROM campaigns WHERE campaignId = :campaignId")
        fun getCampaign(campaignId: Int): Flow<Campaign>

        @Query("SELECT * from campaigns")
        fun getAllCampaigns(): Flow<List<Campaign>>

        /*It is recommended to use Flow in the persistence layer. With Flow as the return type, you
         receive notification whenever the data in the database changes. The Room keeps this Flow
          updated for you, which means you only need to explicitly get the data once. */
    }
}