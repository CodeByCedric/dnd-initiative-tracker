package be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import be.howest.ti.nma.dungeonsanddragonsinitiativetracker.data.db.entities.PlayerCharacter

@Dao
interface PlayerCharacterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(playerCharacter: PlayerCharacter): Long
}